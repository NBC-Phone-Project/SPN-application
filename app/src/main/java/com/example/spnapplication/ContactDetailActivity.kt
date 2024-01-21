package com.example.spnapplication

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.const.IntentKeys.USER_INFO
import com.example.spnapplication.databinding.ActivityContactDetailBinding

class ContactDetailActivity : AppCompatActivity() {

    private val userInfo: UserInfo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            intent.getParcelableExtra(USER_INFO,UserInfo::class.java)
        } else {
            intent.getParcelableExtra<UserInfo>(USER_INFO)
        }
    }

    private val binding by lazy { ActivityContactDetailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            tvUsername.text = userInfo?.userName
            tvNumber.text = userInfo?.userNumber
            tvEmail.text = userInfo?.userEmail
        }
    }
}