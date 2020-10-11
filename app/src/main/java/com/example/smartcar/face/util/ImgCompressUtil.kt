package com.example.smartcar.face.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Size
import java.io.*

/**
 * @Author: LiuYiXin
 * @Date: 2019/12/28 15:24
 * @Description: 压缩工具类
 */
object ImgCompressUtil {
    class CompressConfig(builder: Builder) {
        internal var sampleSize: Int = 1

        internal var resize: Size? = null

        internal var quality: Int = 60
        internal var format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG

        init {
            this.sampleSize = builder.sampleSize
            this.resize = builder.resize
            this.quality = builder.quality
            this.format = builder.format
        }

        class Builder() {
            //采样值。对应BitmapFactory.Options.inSampleSize。等于1的时候将不采样，直接进行压缩。
            //小于1的时候将默认为1
            internal var sampleSize: Int = 1
            internal var resize: Size? = null

            //压缩质量比例。默认为60（luban压缩库的经验值）
            internal var quality: Int = 60

            //图片压缩最终的格式，默认为JPEG。
            internal var format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG

            fun setSampleSize(sampleSize: Int): Builder {
                if (sampleSize < 1) {
                    this.sampleSize = 1
                } else {
                    this.sampleSize = sampleSize
                }
                return this
            }

            fun setResize(resize: Size?): Builder {
                this.resize = resize
                return this
            }

            fun setQuality(quality: Int): Builder {
                this.quality = quality
                return this
            }

            fun setFormat(format: Bitmap.CompressFormat): Builder {
                this.format = format
                return this
            }

            fun build(): CompressConfig {
                return CompressConfig(
                    this
                )
            }
        }
    }


    /**
     * 将目标图片压缩为
     * @param bmp 输入bitmap
     * @param config 压缩配置
     * @return 结果
     */
    fun compress2bmp(bmp: Bitmap, config: CompressConfig): Bitmap {
        val bytes: ByteArray = compress2byteArrayOS(
            bmp,
            config
        ).toByteArray()
        var result = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        return result
    }

    /**
     * 将目标图片压缩为文件。如果最后需要的目标对象是文件建议采用此方法，避免转换成bitmap后再通过流输入到文件的耗时
     * 文件最终的路径由[targetDir]、[fileName]、[config.format]共同决定
     * @param bmp 输入输入bitmap
     * @param config 压缩配置
     * @param targetDir 目标文件夹。请调用者自行保证此文件夹不为空
     * @param fileName 目标文件名
     * @return 结果的文件路径
     */
//    fun compress2file(
//        bmp: Bitmap,
//        config: CompressConfig,
//        targetDir: String,
//        fileName: String
//    ): String {
//        val stream = compress2byteArrayOS(bmp, config)
//
//        val targetPath: String
//        if (targetDir.endsWith(File.separator)) {
//            targetPath = "${targetDir}${fileName}${getImgFileExt(config.format)}"
//        } else {
//            targetPath = "${targetDir}${File.separator}${fileName}${getImgFileExt(config.format)}"
//        }
//
//        try {
//            FileUtils.createFileOrExists(targetPath)
//            val fos = FileOutputStream(targetPath)
//            fos.write(stream.toByteArray())
//            fos.flush()
//            fos.close()
//            stream.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return targetPath
//    }
//
//    fun getImgFileExt(format: Bitmap.CompressFormat): String {
//        val result: String
//        when (format) {
//            Bitmap.CompressFormat.WEBP -> {
//                result = ".webp"
//            }
//            Bitmap.CompressFormat.JPEG -> {
//                result = ".jpg"
//            }
//            Bitmap.CompressFormat.PNG -> {
//                result = ".png"
//            }
//            else -> {
//                result = ".jpg"
//            }
//        }
//        return result
//    }

    /**
     * 将目标图片压缩为字节流。
     * @param bmp 输入输入bitmap
     * @param config 压缩配置
     * @return 结果
     */
    private fun compress2byteArrayOS(bmp: Bitmap, config: CompressConfig): ByteArrayOutputStream {
        var tagBitmap: Bitmap?
        val stream = ByteArrayOutputStream()

        if (config.sampleSize > 1) {
            var inputStream =
                bitmap2InputStream(
                    bmp,
                    config
                )
            val options = BitmapFactory.Options()
            options.inSampleSize = config.sampleSize

            tagBitmap = BitmapFactory.decodeStream(inputStream, null, options)
            tagBitmap?.compress(config.format, config.quality, stream)
            //因为是读出输入流再操作的，主动回收此bitmap降低内存占用
            tagBitmap?.safeRecycle()
        } else {
            //
            tagBitmap = bmp
            config.resize.let {
                if (it != null){
                    tagBitmap =
                        scaleBitmap(
                            bmp,
                            it.width,
                            it.height
                        )
                }
                tagBitmap?.compress(config.format, config.quality, stream)
                if (it != null){
                    //经过缩放产生了一个新的bitmap，需要对其进行回收
                    tagBitmap?.safeRecycle()
                }
            }
        }

        return stream
    }


    private fun bitmap2InputStream(bitmap: Bitmap, config: CompressConfig): InputStream {
        var newBitmap = bitmap
        config.resize?.let {
            newBitmap =
                scaleBitmap(
                    bitmap,
                    it.width,
                    it.height
                )
        }
        val baos = ByteArrayOutputStream()
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byteArray = baos.toByteArray()
        return ByteArrayInputStream(byteArray)
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    private fun scaleBitmap(origin: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        val height = origin.height
        val width = origin.width
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight) // 使用后乘
        val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        return newBM
    }
}