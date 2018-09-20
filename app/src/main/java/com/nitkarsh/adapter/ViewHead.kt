package com.nitkarsh.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.nitkarsh.nitkarshplayer.R

class ViewHead(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var tvHead: TextView? = null

    init {
        tvHead = itemView.findViewById(R.id.text_head)
    }

    fun getTvHead(): TextView? {
        return tvHead
    }

    fun setTvHead(tvHead: TextView) {
        this.tvHead = tvHead
    }
}