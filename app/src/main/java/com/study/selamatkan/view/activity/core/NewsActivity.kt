package com.study.selamatkan.view.activity.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.selamatkan.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }
}