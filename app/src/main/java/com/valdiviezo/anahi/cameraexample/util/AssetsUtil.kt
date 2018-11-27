package com.valdiviezo.anahi.cameraexample.util

import android.content.Context
import com.valdiviezo.anahi.cameraexample.Frame
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream


class AssetsUtil {

    companion object {
        fun getBitmapFromAsset(context: Context, filePath: String): Bitmap {
            val assetManager = context.assets

            val istr: InputStream
            lateinit var bitmap: Bitmap
          //  try {
                istr = assetManager.open(filePath)
                bitmap = BitmapFactory.decodeStream(istr)
          //  } catch (e: IOException) {
                // handle exception
            //}

            return bitmap
        }
    }

}