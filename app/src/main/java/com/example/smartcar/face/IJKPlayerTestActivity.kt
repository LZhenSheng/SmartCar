package com.example.smartcar.face

import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcar.R
import com.example.smartcar.face.rectView.DrawInfo
import com.google.gson.Gson
import com.example.smartcar.face.util.ImgCompressUtil
import com.example.smartcar.face.util.safeRecycle
import com.example.smartcar.face.util.scale
import com.example.smartcar.face.util.transToAndroid
import kotlinx.android.synthetic.main.activity_ijkplayer_test.*
import kotlinx.coroutines.*
import org.dp.facedetection.Face
import org.dp.facedetection.FaceDetect
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class IJKPlayerTestActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    companion object {
        private const val TAG = "IJKPlayerTestActivity"

        //采样间隔时间
        private val SAMPLE_TIME = 200L

        private val DETECTION_SIZE = Size(800, 450)
    }

    private var surface: Surface? = null
    private val ijkMediaPlayer: IjkMediaPlayer by lazy {
        return@lazy IjkMediaPlayer()
    }
    private val sourceStr = "rtmp://58.200.131.2:1935/livetv/hunantv"


    private val compressConfig: ImgCompressUtil.CompressConfig by lazy {
        ImgCompressUtil.CompressConfig.Builder()
            .setResize(DETECTION_SIZE)
            .build()
    }
    private var lastTimeStamp = 0L
    private var compressScale = 1f

    private val faceDetect: FaceDetect by lazy {
        return@lazy FaceDetect()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ijkplayer_test)

        sourceEv?.setText(sourceStr)
        startPlayBt?.setOnClickListener {
            startPlay()
        }
        textureView?.setSurfaceTextureListener(object :
            TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surfaceTexture: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.v(TAG, "onSurfaceTextureAvailable,width:${width},height:${height}")
                surface = Surface(surfaceTexture)
                compressScale = width.toFloat() / DETECTION_SIZE.width.toFloat()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                Log.v(TAG, "onSurfaceTextureUpdated")
                val nowTime = System.currentTimeMillis()
                if (nowTime - lastTimeStamp < SAMPLE_TIME) {
                    //500ms采样
                    return
                }
                lastTimeStamp = nowTime

                val bitmap = textureView.bitmap
//                imageView.setImageBitmap(bitmap)

                launch(Dispatchers.IO) {
                    //图片压缩一下，否则人脸识别native内存消耗会很大
                    val bmp = ImgCompressUtil.compress2bmp(bitmap, compressConfig)
                    bitmap.safeRecycle()
                    val faces: Array<Face>? = faceDetect.doFaceDetect(bmp)
                    val faceDrawInfo = ArrayList<DrawInfo>()
                    if (!faces.isNullOrEmpty()) {
                        for (face in faces) {
                            Log.v(TAG, "face detection,rect:${face.faceRect.toString()}")
                            Log.v(TAG, "face detection,confidence:${face.faceConfidence}\n")

                            val drawInfo = DrawInfo(
                                face.faceRect.transToAndroid().scale(compressScale),
                                0, 0, 0, 0, ""
                            )
                            faceDrawInfo.add(drawInfo)
                        }
                    }
                    Log.d(TAG, "face detection,faceDrawInfo:${Gson().toJson(faceDrawInfo)}")
                    withContext(Dispatchers.Main) {
                        faceRectView.clearFaceInfo()
                        faceRectView.addFaceInfo(faceDrawInfo)
                    }

                    Log.v(TAG, "face detection-----")
                    bmp.safeRecycle()
                }

            }
        })
    }

    fun startPlay() {
        if (null == surface) {
            Log.d(TAG, "null == surface")
            return
        }

        ijkMediaPlayer.reset()
        try {
            ijkMediaPlayer.setSurface(surface)
            ijkMediaPlayer.dataSource = sourceEv?.text.toString() ?: sourceStr
            ijkMediaPlayer.setOnPreparedListener { iMediaPlayer ->
                Log.d(
                    TAG,
                    "videoWidth:${ijkMediaPlayer.videoWidth},videoHeight:${ijkMediaPlayer.videoHeight}"
                )
                iMediaPlayer.start()
            }
            ijkMediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        ijkMediaPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        ijkMediaPlayer?.stop()
        ijkMediaPlayer?.release()
    }
}