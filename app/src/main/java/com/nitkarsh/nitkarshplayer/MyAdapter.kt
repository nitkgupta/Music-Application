package com.nitkarsh.nitkarshplayer

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MyAdapter(context: Context, activity: MainActivity, list: ArrayList<Audio>, check: Checkandwork) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private lateinit var title: TextView
    private lateinit var artist: TextView
    private lateinit var album: TextView
    private var context: Context = context
    private var list: ArrayList<Audio> = list
    private var activity: MainActivity = activity
    private var checkandwork: Checkandwork = check

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_recycler, parent, false)
        var viewHolder = ViewHolder(view, context, activity, list, checkandwork)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        album = holder!!.album
        artist = holder!!.artist
        title = holder!!.title
        album.setText(list.get(position).album)
        artist.setText(list.get(position).artist)
        title.setText(list.get(position).title)
    }

    class ViewHolder(view: View, context: Context, activity: MainActivity, list: ArrayList<Audio>, check: Checkandwork) : RecyclerView.ViewHolder(view) {
        public lateinit var title: TextView
        public lateinit var artist: TextView
        public lateinit var album: TextView
        public lateinit var constraintLayout: ConstraintLayout
        public var checkandwork = check

        init {
            title = view.findViewById(R.id.title)
            artist = view.findViewById(R.id.artist)
            album = view.findViewById(R.id.album)
            constraintLayout = view.findViewById(R.id.tink)
            constraintLayout.setOnClickListener {
                checkandwork.update(adapterPosition)
            }

        }
    }

    interface Checkandwork {
        fun update(pos: Int)
    }
}