package com.nitkarsh.nitkarshplayer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.nitkarsh.gallery.PermissionCheck
import java.util.*


class SplashActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<String>
    private var isPermissionGranted: Boolean = false
    private var PERMISSION_GIVEN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@SplashActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_GIVEN)
        } else {
            var timer = Timer()
            var task = Helper(this@SplashActivity)
            timer.schedule(task, 0, 2000)
        }

    }

    class Helper(context: Activity) : TimerTask() {
        private lateinit var context: Activity

        init {
            this.context = context
        }

        private var i = 1
        override fun run() {
            i = ++i
            if (i == 3) {
                var intent = Intent(context.applicationContext, MainActivity::class.java)
                context.startActivity(intent)
                context.finish()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var view = findViewById<View>(android.R.id.content)
        if (grantResults.get(0) == -1) {

            Snackbar.make(view, "Please grant Permissions for this app to work.", Snackbar.LENGTH_INDEFINITE).setAction("Give Permissions", View.OnClickListener {
                var check = PermissionCheck(this, this@SplashActivity)
                check.permissionGranted()
            }).show()
        } else if (grantResults.get(0) == 0) {
            isPermissionGranted = true
            var timer = Timer()
            var task = Helper(this@SplashActivity)
            timer.schedule(task, 0, 1000)

        }
    }
}
