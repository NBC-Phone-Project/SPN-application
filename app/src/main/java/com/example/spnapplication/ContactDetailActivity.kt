package com.example.spnapplication

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.databinding.ActivityContactDetailBinding

class ContactDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityContactDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            tvUsername.text = UserInfoItem?.name
            tvNumber.text = UserInfoItem?.phoneNumber
            tvEmail.text = UserInfoItem?.email
        }
    }

    private val UserInfoItem: UserInfo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra("UserInfo",UserInfo::class.java)
        } else {
            intent.getParcelableExtra<UserInfo>("UserInfo")
        }
    }
}