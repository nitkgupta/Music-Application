package com.nitkarsh.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.nitkarsh.nitkarshplayer.R

class ViewTags(context: Context, itemView: View, drawerLayout: DrawerLayout, layoutManager: RecyclerView.LayoutManager) : RecyclerView.ViewHolder(itemView) {
    private var imageTag: ImageView? = null
    private var textTag: TextView? = null
    private lateinit var constraintLayout: ConstraintLayout
    private var last_position = -1

    init {
        imageTag = itemView.findViewById(R.id.image_tag)
        textTag = itemView.findViewById(R.id.text_tag)
        constraintLayout = itemView.findViewById(R.id.layout_inflate)
        constraintLayout.setOnClickListener {
            if (last_position == -1) {
                constraintLayout.setBackgroundColor(context.resources.getColor(R.color.colorTint))
                last_position = adapterPosition
            } else {
                constraintLayout.setBackgroundColor(context.resources.getColor(R.color.colorTint))
                constraintLayout = layoutManager.findViewByPosition(last_position).findViewById(R.id.layout_inflate)
                constraintLayout.setBackgroundColor(context.resources.getColor(R.color.colorGone))
                last_position = adapterPosition
                constraintLayout = layoutManager.findViewByPosition(last_position).findViewById(R.id.layout_inflate)
            }
            Toast.makeText(context, "Item Clicked at Position $adapterPosition", Toast.LENGTH_LONG).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun getImageTag(): ImageView? {
        return imageTag
    }

    fun setImageTag(imageTag: ImageView) {
        this.imageTag = imageTag
    }

    fun getTextTag(): TextView? {
        return textTag
    }

    fun setTextTag(textTag: TextView) {
        this.textTag = textTag
    }
}

