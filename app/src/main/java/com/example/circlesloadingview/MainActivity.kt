package com.example.circlesloadingview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.circlesloadingview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val circlesLoadingView = binding.circleLoadingView

    }

}