package com.first_android_project.orchestraeshop

import android.view.View

interface ItemClickListener {
    fun onClick(view: View?, position: Int, isLongClicked: Boolean) {

    }
}