package com.example.spnapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.databinding.ActivityContactDetailBinding
import com.google.android.material.snackbar.Snackbar

class ContactDetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityContactDetailBinding.inflate(layoutInflater) }
    private var isLike = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val userList = Const.DummyData
        val adapter = UserAdapter(userList)

        with(binding) {
            UserInfoItem?.let { ivUserImage.setImageResource(it.userImage) }
            UserInfoItem?.let { ivUserImage.setImageURI(it.profileImage) }
            tvUsername.text = UserInfoItem?.userName
            tvNumber.text = UserInfoItem?.userNumber
            tvEmail.text = UserInfoItem?.userEmail
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
    }

    private val UserInfoItem: UserInfo? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("UserInfo", UserInfo::class.java)
        } else {
            intent.getParcelableExtra<UserInfo>("UserInfo")
        }
    }
}