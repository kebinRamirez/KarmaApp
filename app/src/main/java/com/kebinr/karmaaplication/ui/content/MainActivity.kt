package com.kebinr.karmaaplication.ui.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kebinr.karmaaplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar2))
    }
}