package com.example.spnapplication.contactDetail

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.spnapplication.R
import com.example.spnapplication.UserInfo
import com.example.spnapplication.const.DummyUserInfo
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
        }
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
            tvUsername.text = userInfo?.userName
            tvNumber.text = userInfo?.userNumber
            tvEmail.text = userInfo?.userEmail
            userInfo?.isLike?.let { updateLikeIcon(it) }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}