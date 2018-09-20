package com.nitkarsh.gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.nitkarsh.nitkarshplayer.SplashActivity


class PermissionCheck(context: Context, activity: SplashActivity) {
    private lateinit var context: Context
    private lateinit var activity: SplashActivity
    private var VAR_CHECK_PERMISSION = 1

    init {
        this.context = context
        this.activity = activity
    }

    fun permissionGranted(): Boolean {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            var view = activity.findViewById<View>(android.R.id.content)
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), VAR_CHECK_PERMISSION)

            } else {
                var intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
        }
        return true
    }
}