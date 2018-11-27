package com.valdiviezo.anahi.cameraexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.valdiviezo.anahi.cameraexample.util.AssetsUtil


class Frame (frameName: String, left: Int, top: Int, right: Int, bottom: Int, rorate: Float) {
    //filename of frame
    private lateinit var mFrameName: String

    //Rect of picture area in frame
    private lateinit var mPictureRect: Rect

    //degree of rotation to fit picture and frame.
    private var mRorate: Float = 0.00f

    init {
        mFrameName = frameName
        mPictureRect = Rect(left, top, right, bottom)
        mRorate = rorate
    }


    fun mergeWith(context: Context, pictureBitmap: Bitmap): Bitmap {
        lateinit var frameBitmap : Bitmap
        frameBitmap= AssetsUtil.getBitmapFromAsset(context, mFrameName)

        val conf = Bitmap.Config.ARGB_8888
        val bitmap = Bitmap.createBitmap(frameBitmap.getWidth(), frameBitmap.getHeight(), conf)
        val canvas = Canvas(bitmap)

        val matrix = getMatrix(pictureBitmap)
        canvas.drawBitmap(pictureBitmap, matrix, null)

        //canvas.drawBitmap(frameBitmap, 0, 0, null)

        return bitmap

    }

    fun getMatrix(pictureBitmap: Bitmap): Matrix {
        val widthRatio = mPictureRect.width() / pictureBitmap.width.toFloat()
        val heightRatio = mPictureRect.height() / pictureBitmap.height.toFloat()

        val ratio: Float

        if (widthRatio > heightRatio) {
            ratio = widthRatio

        } else {
            ratio = heightRatio
        }

        val width = pictureBitmap.width * ratio
        val height = pictureBitmap.height * ratio
        val left = mPictureRect.left - (width - mPictureRect.width()) / 2f
        val top = mPictureRect.top - (height - mPictureRect.height()) / 2f

        val matrix = Matrix()
        matrix.postRotate(mRorate)
        matrix.postScale(ratio, ratio)
        matrix.postTranslate(left, top)

        return matrix
    }
}