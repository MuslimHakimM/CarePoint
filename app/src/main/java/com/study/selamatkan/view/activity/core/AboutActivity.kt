package com.study.selamatkan.view.activity.core

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.study.selamatkan.R
import com.study.selamatkan.databinding.ActivityAboutBinding
import com.study.selamatkan.utils.HelpUtil

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            HelpUtil.setStatusBarColor(this@AboutActivity, R.color.white, root)
            toolbar.setNavigationOnClickListener { onBackPressed() }
            tvDesc.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}