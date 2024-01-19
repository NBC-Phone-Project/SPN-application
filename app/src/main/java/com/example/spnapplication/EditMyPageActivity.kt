package com.example.spnapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.spnapplication.databinding.ActivityEditMyPageBinding

class EditMyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditMyPageBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}