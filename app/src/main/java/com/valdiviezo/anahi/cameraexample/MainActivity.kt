package com.valdiviezo.anahi.cameraexample

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.provider.MediaStore
import android.content.Intent
import android.widget.Toast
import android.R.attr.data
import android.app.Activity
import android.support.v4.app.NotificationCompat.getExtras
import android.graphics.Bitmap
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.valdiviezo.anahi.cameraexample.util.AssetsUtil
import android.app.AlertDialog
import android.net.Uri
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.karumi.dexter.Dexter
import android.provider.Settings
import com.karumi.dexter.listener.PermissionRequest


class MainActivity : AppCompatActivity() {
    private val Image_Capture_Code : Int = 8888

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnTakePicture.setOnClickListener {
            requestCameraPermission()
        }
    }

    fun openCamera() {
        val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cInt, Image_Capture_Code)

    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }

     /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
     private fun showSettingsDialog() {
         val builder = AlertDialog.Builder(this@MainActivity)
         builder.setTitle("Need Permissions")
         builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
         builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
             dialog.cancel()
             openSettings()
         }
         builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
         builder.show()

     }
    /**
     * Requesting camera permission
     * This uses single permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */
    private fun requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        // permission is granted
                        mergePictures()
                       // openCamera()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied) {
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    fun mergePictures() {

        //This is sample picture.
//Please take picture form gallery or camera.
        val pictureBitmap: Bitmap = AssetsUtil.getBitmapFromAsset(this, "/camera/picture.jpg")


//This is sample frame.
// the number of left, top, right, bottom is the area to show picture.
// last argument is degree of rotation to fit picture and frame.
        val frameA = Frame("camera/frame_a.png", 113, 93, 430, 409, 4f)
        val mergedBitmap : Bitmap = frameA.mergeWith(this, pictureBitmap)

        capturedImage.setImageBitmap(mergedBitmap)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === Image_Capture_Code) {
            if (resultCode === Activity.RESULT_OK) {
                val bp = data?.getExtras()?.get("data") as Bitmap
                capturedImage.setImageBitmap(bp)
            } else if (resultCode === Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
        }
    }
}
