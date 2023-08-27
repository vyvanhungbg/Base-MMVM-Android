package com.tieuvy.android.base_mvvm_android_2.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tieuvy.android.base_mvvm_android_2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}