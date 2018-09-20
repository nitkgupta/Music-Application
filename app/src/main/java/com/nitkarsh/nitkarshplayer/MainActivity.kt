package com.nitkarsh.nitkarshplayer

import android.content.*
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.support.constraint.ConstraintLayout
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var searchView: SearchView
    private lateinit var toolbar1: Toolbar
    private lateinit var slidingUpPanelLayout: SlidingUpPanelLayout
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageLoop: ImageView
    private lateinit var imagePrevious: ImageView
    private lateinit var imageNext: ImageView
    private lateinit var imagePlay: ImageView
    private lateinit var imageStop: ImageView
    private lateinit var list: ArrayList<Audio>
    private lateinit var musicService: MusicService
    private var musicBound: Boolean = false
    private var i = 0
    private lateinit var intent1: Intent
    private lateinit var serviceConnection: ServiceConnection
//    private lateinit var list2 : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        toolbar1 = findViewById(R.id.toolbar)
        searchView = findViewById(R.id.search_view)
        slidingUpPanelLayout = findViewById(R.id.sliding_layout)
        imageLoop = findViewById(R.id.image_restart)
        imageNext = findViewById(R.id.image_next)
        imagePlay = findViewById(R.id.image_play)
        imageStop = findViewById(R.id.image_stop)
        imagePrevious = findViewById(R.id.image_previous)
        constraintLayout = slidingUpPanelLayout.findViewById(R.id.constraint_main)
        recyclerView = constraintLayout.findViewById(R.id.recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        var helps = Helps(contentResolver)
        list = helps.execute().get()
//        list2=ArrayList<String>()
//        for(j in 1..list.size){
//            list2.add(list.get(j-1).title!!)
//        }
//        val array = arrayOfNulls<String>(list.size)
//        val arrayAdapter=ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,list2.toArray(array))

        searchView.queryHint="Search Music ..."
        var check = object : MyAdapter.Checkandwork {
            override fun update(pos: Int) {
                if (!musicBound) {
                    imagePlay.setImageResource(R.drawable.pause)
                    musicBound = true
                    i = pos
                    Toast.makeText(this@MainActivity, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                } else {
                    i = pos
                    musicBound = true
                    unbindService(serviceConnection)
                    stopService(intent1)
                    Toast.makeText(this@MainActivity, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                }
            }
        }

        musicService = MusicService()
        intent1 = Intent(this@MainActivity, MusicService::class.java)
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                musicBound = false
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                var binder = service as MusicService.LocalBinder
                musicBound = true
            }
        }
        recyclerView.adapter = MyAdapter(this, this@MainActivity, list, check)

        imagePlay.setOnClickListener { view ->
            if (!musicBound) {
                imagePlay.setImageResource(R.drawable.pause)
                musicBound = true
                Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                intent1.putExtra("dataSource", list.get(i).data)
                bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                startService(intent1)
            } else {
                imagePlay.setImageResource(R.drawable.play)
                musicBound = false
                unbindService(serviceConnection)
                stopService(intent1)
            }

        }

        imageStop.setOnClickListener { view ->
            if (musicBound) {
                imagePlay.setImageResource(R.drawable.play)
                musicBound = false
                Toast.makeText(this, "Stopped Playing Music", Toast.LENGTH_LONG).show()
                unbindService(serviceConnection)
                stopService(intent1)
            } else {
                Toast.makeText(this, "Music Already Stopped", Toast.LENGTH_LONG).show()
            }
        }
        imagePrevious.setOnClickListener {
            if (musicBound) {
                if (i <= 1) {
                    i--;
                    unbindService(serviceConnection)
                    stopService(intent1)
                    Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                } else if (i == 0) {
                    Toast.makeText(this, "Can't go back anymore.", Toast.LENGTH_LONG).show()
                }
            } else {
                if (i <= 1) {
                    i--;
                    imagePlay.setImageResource(R.drawable.pause)
                    musicBound = true
                    Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                } else if (i == 0) {
                    Toast.makeText(this, "Can't go back anymore.", Toast.LENGTH_LONG).show()
                }

            }
        }

        imageNext.setOnClickListener {
            if (musicBound) {
                if (i <= list.size) {
                    i++;
                    unbindService(serviceConnection)
                    stopService(intent1)
                    Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                } else if (i == list.size) {
                    Toast.makeText(this, "Can't go next anymore.", Toast.LENGTH_LONG).show()
                }
            } else {
                if (i <= list.size) {
                    i++;
                    imagePlay.setImageResource(R.drawable.pause)
                    musicBound = true
                    Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                    intent1.putExtra("dataSource", list.get(i).data)
                    bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                    startService(intent1)
                } else if (i == list.size) {
                    Toast.makeText(this, "Can't go next anymore.", Toast.LENGTH_LONG).show()
                }
            }
        }

        imageLoop.setOnClickListener {
            if (musicBound) {
                unbindService(serviceConnection)
                stopService(intent1)
                Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                intent1.putExtra("dataSource", list.get(i).data)
                bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                startService(intent1)
            } else {
                imagePlay.setImageResource(R.drawable.pause)
                musicBound = true
                Toast.makeText(this, "Playing Music : ${list.get(i).title}", Toast.LENGTH_LONG).show()
                intent1.putExtra("dataSource", list.get(i).data)
                bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE)
                startService(intent1)
            }

        }


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    class Helps(contentResolver: ContentResolver) : AsyncTask<String, String, ArrayList<Audio>>() {
        public lateinit var contentResolver: ContentResolver
        public lateinit var list: ArrayList<Audio>


        init {
            this.contentResolver = contentResolver
        }

        override fun doInBackground(vararg params: String?): ArrayList<Audio> {
            loadAudio()
            return list
        }


        private fun loadAudio() {

            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
            val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
            val cursor = contentResolver.query(uri, null, selection, null, sortOrder)
            if (cursor != null && cursor.count > 0) {
                list = ArrayList<Audio>()
                while (cursor.moveToNext()) {
                    var data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    var title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    var album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    var artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    var audio = Audio(data, title, album, artist)
                    list.add(audio)
                }
                println(list.size)
            }
            cursor.close()
        }

        override fun onPostExecute(result: ArrayList<Audio>?) {
            super.onPostExecute(result)

        }
    }

}
