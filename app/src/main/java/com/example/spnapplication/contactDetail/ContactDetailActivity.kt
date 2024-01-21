package com.example.spnapplication.contactDetail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.R
import com.example.spnapplication.UserInfo
import com.example.spnapplication.const.DummyUserInfo
import com.example.spnapplication.const.IntentKeys.SMS_BODY
import com.example.spnapplication.const.IntentKeys.USER_INFO
import com.example.spnapplication.databinding.ActivityContactDetailBinding

class ContactDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityContactDetailBinding.inflate(layoutInflater) }

    private val userInfo: UserInfo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(USER_INFO, UserInfo::class.java)
        } else {
            intent.getParcelableExtra<UserInfo>(USER_INFO)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            ivActionBarBack.setOnClickListener { finish() }
            ivActionBarLike.setOnClickListener { handleLikeAction() }
            ivActionBarDelete.setOnClickListener { handleDeleteAction() }
            tvSendMessage.setOnClickListener { sendMessage() }
            tvMakeCall.setOnClickListener { makeCall() }
        }
    }

    private fun makeCall() {
        // 전화 걸 대상의 전화번호
        val phoneNumber = userInfo?.phoneNumber

        // 전화 걸기 Intent 생성
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")

        // Intent 실행
        startActivity(intent)
    }

    private fun sendMessage() {
        // 받는 사람의 전화번호
        val phoneNumber = userInfo?.phoneNumber

        // 메시지 작성 앱으로 이동하는 Intent 생성
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phoneNumber")
        intent.putExtra(SMS_BODY, "${userInfo?.name} 안녕하세요, 메시지 내용입니다.")

        // Intent 실행
        startActivity(intent)
    }

    private fun handleDeleteAction() {
        userInfo?.let {
            DummyUserInfo.removeUserByUserInfo(it)
            finish()
        }
        showToast("Can't find user")
    }

    private fun handleLikeAction() {
        userInfo?.let {
            val toggledState = DummyUserInfo.toggleLikeState(it)
            updateLikeIcon(toggledState)
            showToast(if (toggledState) "Liked!" else "UnLiked!")
        }
    }

    private fun updateLikeIcon(toggledState: Boolean) {
        binding.ivActionBarLike.setImageResource(
            if (toggledState) {
                R.drawable.ic_contact_detail_kid_star_filled_24dp
            } else {
                R.drawable.ic_contact_detail_kid_star_24dp
            }
        )
    }

    private fun initView() {
        with(binding) {
            tvUsername.text = userInfo?.name
            tvNumber.text = userInfo?.phoneNumber
            tvEmail.text = userInfo?.email
            userInfo?.isLike?.let { updateLikeIcon(it) }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}