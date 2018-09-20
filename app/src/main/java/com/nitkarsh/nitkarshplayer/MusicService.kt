package com.nitkarsh.nitkarshplayer

import android.app.Service
import android.content.*
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.ImageView

class MusicService : Service(), MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private lateinit var mediaPlayer: MediaPlayer
    var iBinder = LocalBinder()
    private lateinit var dataSource: String
    private lateinit var broadcastReceiver:BroadcastReceiver

    override fun onCompletion(mp: MediaPlayer?) {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.i("SERVICE_ERROR", "$what         $extra")
        mediaPlayer.reset()
        mediaPlayer.release()
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer.start()
    }

    fun pauseMedia(){

        mediaPlayer.pause()
    }
    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        broadcastReceiver=MyBroadcastReceiver(mediaPlayer)
        var intentFilter = IntentFilter("com.nitkarsh.broadcast.SOME_ACTION");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnErrorListener(this)

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        dataSource = intent!!.getStringExtra("dataSource")
        mediaPlayer.setDataSource(dataSource)
        mediaPlayer.prepareAsync()
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent): IBinder {
        return iBinder
    }


    public fun onPauses() {
        if (mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            Log.i("SERVICE_ERROR", "Service stopped before pausing")
            stopSelf()

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        stopSelf()

    }


    inner class LocalBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }


    class MyBroadcastReceiver constructor(mediaPlayer: MediaPlayer)  : BroadcastReceiver()  {
        var mediaPlayer=mediaPlayer
        companion object {
            var length=1
        }
        override fun onReceive(context: Context, intent: Intent) {
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
            length=mediaPlayer.currentPosition
        }
            else{
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
        }
        }
    }

}
