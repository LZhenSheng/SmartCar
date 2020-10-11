package com.example.smartcar.application

import android.app.Application
import org.dp.facedetection.FaceDetect

/**
 * @Author: LiuYiXin
 * @Date: 2020/7/1 8:31
 * @Description:
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FaceDetect.init()
    }
}