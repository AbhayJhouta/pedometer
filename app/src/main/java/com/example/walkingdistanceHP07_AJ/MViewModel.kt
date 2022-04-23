package com.example.walkingdistanceHP07_AJ

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MViewModel(val shrd:SharedPreferences):ViewModel() {
    fun getsteps():Int
    {
        val steps=shrd.getInt("cur",0);
        return steps
    }
}