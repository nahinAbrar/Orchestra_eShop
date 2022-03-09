package com.first_android_project.orchestraeshop.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrdersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No Orders Available"
    }
    val text: LiveData<String> = _text
}