package com.kebinr.karmaaplication.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kebinr.karmaaplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setSupportActionBar(findViewById(R.id.toolbar))
        baseContext
    }
}