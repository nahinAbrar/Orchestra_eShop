package com.first_android_project.orchestraeshop.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No Products Added to the Cart"
    }
    val text: LiveData<String> = _text
}