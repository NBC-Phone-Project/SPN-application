package com.example.spnapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.spnapplication.databinding.ActivityEditMyPageBinding
import com.example.spnapplication.utils.Utils

class EditMyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditMyPageBinding.inflate(layoutInflater) }

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {

            val name = binding.etMyName.text.toString()
            val tel = binding.etMyPhoneNumber.text.toString()
            val email = binding.etMyEmail.text.toString()
            val memo = binding.etMyMemo.text.toString()


            Log.d("jblee", "imageUri = ${imageUri}")
            Log.d("jblee", "name = ${name}")
            Log.d("jblee", "tel = ${tel}")
            Log.d("jblee", "email = ${email}")
            Log.d("jblee", "memo = ${memo}")



            val newMyInfo = MyInfo(uriToString(imageUri!!), name, tel, email, memo)
            Utils.savePrefUser(this, newMyInfo)
            Log.d("jblee", "myInfo = $newMyInfo")
            finish()
        }

        binding.ivAddPic.setOnClickListener {
            openGallery(this, 111)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 111) {
            imageUri = data?.data
            binding.ivEditMyProfile.setImageURI(imageUri)
        }
    }

    fun openGallery(activity: Activity, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, requestCode)
    }

    fun uriToString(uri: Uri): String {
        return uri.toString()
    }

}