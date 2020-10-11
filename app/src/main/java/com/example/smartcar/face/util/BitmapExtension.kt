package com.example.smartcar.face.util

import android.graphics.Bitmap

/**
 * @Author: LiuYiXin
 * @Date: 2020/3/26 10:46
 * @Description: bitmap扩展函数
 */

/**
 * 安全回收。避免已经被回收过的bitmap重新调用一次回收导致的native错误
 */
fun Bitmap.safeRecycle(){
    if (!this.isRecycled){
        this.recycle()
    }
}