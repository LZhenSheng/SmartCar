package com.example.smartcar.face.util

import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Size
import org.opencv.osgi.OpenCVInterface

/**
 * @Author: LiuYiXin
 * @Date: 2020/3/26 10:46
 * @Description: rect
 */

/**
 * 将openCV的rect转为Android的rect
 */
fun org.opencv.core.Rect.transToAndroid(): Rect {
    return Rect(this.x,this.y,this.x+this.width,this.y+this.height)
}

fun Rect.scale(scale: Float): Rect {
    val rect = Rect(
        (this.left * scale).toInt(),
        (this.top * scale).toInt(),
        (this.right * scale).toInt(),
        (this.bottom * scale).toInt()
    )
    return rect
}

fun Rect.toSize(): Size {
    val size = Size(this.width(), this.height())
    return size
}

fun Rect.toDes(): String {
    return "${this.left}_${this.top}_${this.right}_${this.bottom}"
}