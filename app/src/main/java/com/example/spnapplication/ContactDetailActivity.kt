package com.example.spnapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.const.DummyUserInfo
import com.example.spnapplication.const.IntentKeys.SMS_BODY
import com.example.spnapplication.databinding.ActivityContactDetailBinding
import com.google.android.material.snackbar.Snackbar

class ContactDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityContactDetailBinding.inflate(layoutInflater) }
    private var isLike = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val userList = DummyUserInfo.DummyData
        val adapter = UserAdapter(userList)

        with(binding) {
            //기본이미지, Uri 이미지 출력
            UserInfoItem?.image?.let { ivUserImage.setImageResource(it) }
            tvUsername.text = UserInfoItem?.name
            tvNumber.text = UserInfoItem?.phoneNumber
            tvEmail.text = UserInfoItem?.email
            isLike = UserInfoItem?.isLike == true

            ivActionBarLike.setImageResource(
                if (isLike) {
                    R.drawable.ic_contact_detail_kid_star_filled_24dp
                } else {
                    R.drawable.ic_contact_detail_kid_star_24dp
                }
            )
            ivActionBarLike.setOnClickListener {
                isLike = if (isLike) {
                    ivActionBarLike.setImageResource(R.drawable.ic_contact_detail_kid_star_24dp)
                    false
                } else {
                    ivActionBarLike.setImageResource(R.drawable.ic_contact_detail_kid_star_filled_24dp)
                    Snackbar.make(
                        binding.clBody,
                        "즐겨찾기에 추가되었습니다.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    true
                }
            }
        }
        binding.ivActionBarBack.setOnClickListener {
            val likePosition = intent.getIntExtra("likePosition", 0)
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("likePosition", likePosition)
                putExtra("isLike", isLike)
            }
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }

        binding.ivActionBarDelete.setOnClickListener {
            val deletePosition = intent.getIntExtra("likePosition", 0)
            val postDelete = userList[deletePosition]
            userList.remove(postDelete)
            val intent = Intent(this, ContactFragment::class.java)
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }

        binding.tvSendMessage.setOnClickListener {
            sendMessage()
        }
        binding.tvMakeCall.setOnClickListener {
            makeCall()
        }
    }

    private val UserInfoItem: UserInfo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("UserInfo", UserInfo::class.java)
        } else {
            intent.getParcelableExtra<UserInfo>("UserInfo")
        }
    }

    private fun makeCall() {
        // 전화 걸 대상의 전화번호
        val phoneNumber = UserInfoItem?.phoneNumber

        // 전화 걸기 Intent 생성
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")

        // Intent 실행
        startActivity(intent)
    }

    private fun sendMessage() {
        // 받는 사람의 전화번호
        val phoneNumber = UserInfoItem?.phoneNumber

        // 메시지 작성 앱으로 이동하는 Intent 생성
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phoneNumber")
        intent.putExtra(SMS_BODY, "${UserInfoItem?.name} 안녕하세요, 메시지 내용입니다.")

        // Intent 실행
        startActivity(intent)
    }
}