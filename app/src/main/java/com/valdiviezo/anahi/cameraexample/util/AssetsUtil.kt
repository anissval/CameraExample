package com.valdiviezo.anahi.cameraexample.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.os.Environment
import java.io.IOException
import java.io.InputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AssetsUtil {

    companion object {
        var imageFilePath : String? = null

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


        @Throws(IOException::class)
        fun createImageFile(context: Context): File {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(Date())
            val imageFileName = "IMG_" + timeStamp + "_"
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File.createTempFile(
                    imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    storageDir      /* directory */
            )

            imageFilePath = image.absolutePath
            return image
        }

    }

}