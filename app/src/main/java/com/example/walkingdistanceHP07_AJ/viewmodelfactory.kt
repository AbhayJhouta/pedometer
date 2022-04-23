package com.example.walkingdistanceHP07_AJ

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class viewmodelfactory(val shrd:SharedPreferences):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MViewModel(shrd) as T
    }
}