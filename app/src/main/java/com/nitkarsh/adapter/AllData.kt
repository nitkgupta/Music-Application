package com.nitkarsh.adapter

import com.nitkarsh.nitkarshplayer.R

class AllData {
    private val tag = ArrayList<Any>()
    fun getData(): ArrayList<Any> {
        val mData1 = TagData()
        val mData2 = TagData()
        val mData3 = TagData()
        val mData4 = TagData()
        val mData5 = TagData()
        val mData6 = TagData()
        val mData10 = TagData()
        val mData7 = HeadData()
        val mData9 = HeadData()

        mData7.head = "Music"
        tag.add(mData7)
        mData1.image = R.drawable.ic_menu_camera
        mData1.text = "Albums"
        tag.add(mData1)
        mData2.image = R.drawable.ic_menu_gallery
        mData2.text = "Artist"
        tag.add(mData2)
        mData5.image = R.drawable.ic_menu_slideshow
        mData5.text = "Show Recent"
        tag.add(mData5)
        mData4.image = R.drawable.ic_menu_manage
        mData4.text = "Manage"
        tag.add(mData4)
        mData9.head = "Management"
        tag.add(mData9)
        mData3.image = R.drawable.ic_menu_send
        mData3.text = "Send"
        tag.add(mData3)
        mData6.image = R.drawable.ic_menu_share
        mData6.text = "Share"
        tag.add(mData6)
        mData10.image = R.drawable.ic_menu_manage
        mData10.text = "Settings"
        tag.add(mData10)


        return tag
    }
}