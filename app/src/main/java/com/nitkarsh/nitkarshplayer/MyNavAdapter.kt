package com.nitkarsh.nitkarshplayer

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nitkarsh.adapter.HeadData
import com.nitkarsh.adapter.TagData
import com.nitkarsh.adapter.ViewHead
import com.nitkarsh.adapter.ViewTags


class MyNavAdapter(context:Context,list: List<Any>,drawerLayout: DrawerLayout,layoutManager: RecyclerView.LayoutManager): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val context: Context=context
    private val list: List<Any> = list
    private val drawerLayout: DrawerLayout = drawerLayout
    private val layoutManager: RecyclerView.LayoutManager = layoutManager
    private val head = 0
    private val tag = 1

    private val objectList: List<Any>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            tag -> {
                val v1 = inflater.inflate(R.layout.recycler_menu_item, parent, false)
                viewHolder = ViewTags(context, v1, drawerLayout, layoutManager)
            }
            head -> {
                val v2 = inflater.inflate(R.layout.recycler_menu_head, parent, false)
                viewHolder = ViewHead(v2)
            }
            else -> {
                val v3 = inflater.inflate(R.layout.recycler_menu_item, parent, false)
                viewHolder = ViewTags(context, v3, drawerLayout, layoutManager)
            }
        }

        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] is TagData) {
            tag
        } else {
            head
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            tag -> {
                val tags = holder as ViewTags
                val dataTag = list[position] as TagData
                tags.getImageTag()!!.setImageDrawable(ContextCompat.getDrawable(context, dataTag.image))
                tags.getTextTag()!!.setText(dataTag.text)
            }
            head -> {
                val head = holder as ViewHead
                val dataHead = list[position] as HeadData
                head.getTvHead()!!.setText(dataHead.head)
            }
            else -> {
                val defaultTag = holder as ViewTags
                val dataDefault = list[position] as TagData
                defaultTag.getImageTag()!!.setImageDrawable(ContextCompat.getDrawable(context, dataDefault.image))
                defaultTag.getTextTag()!!.setText(dataDefault.text)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}