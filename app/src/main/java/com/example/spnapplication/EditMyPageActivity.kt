package com.example.spnapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.spnapplication.databinding.ActivityEditMyPageBinding
import com.example.spnapplication.utils.Utils
import java.util.regex.Pattern

class EditMyPageActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditMyPageBinding.inflate(layoutInflater) }

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.etMyEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isRegularEmail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etMyPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isRegularName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.btnSave.setOnClickListener {
            var isGotoJoin = true
            val name = binding.etMyName.text.toString()
            val tel = binding.etMyPhoneNumber.text.toString()
            val email = binding.etMyEmail.text.toString()
            val memo = binding.etMyMemo.text.toString()


            Log.d("onCreate", "imageUri = ${imageUri}")
            Log.d("onCreate", "name = ${name}")
            Log.d("onCreate", "tel = ${tel}")
            Log.d("onCreate", "email = ${email}")
            Log.d("onCreate", "memo = ${memo}")



            if (name.isEmpty()) {
                isGotoJoin = false
                Toast.makeText(this@EditMyPageActivity, "공란이 존재합니다.", Toast.LENGTH_SHORT).show()
            } else if (!isRegularName()) {
                isGotoJoin = false
                Toast.makeText(
                    this@EditMyPageActivity,
                    "전화번호를 - 포함 13자리를 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isRegularEmail()) {
                isGotoJoin = false
                Toast.makeText(this@EditMyPageActivity, "이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }

            if (isGotoJoin) {
                val newMyInfo = MyInfo(uriToString(imageUri!!), name, tel, email, memo)
                Utils.savePrefUser(this, newMyInfo)
                Log.d("onCreate", "myInfo = $newMyInfo")
                finish()
            }
        }

        // + 버튼 클릭 시 갤러리로 이동
        binding.ivAddPic.setOnClickListener {
            openGallery(this, 111)
        }

        // 뒤로가기 버튼 클릭 시 finish
        binding.icMypageBack.setOnClickListener {
            finish()
        }

    }

    // 갤러리에서 불러온 사진 띄우기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 111) {
            imageUri = data?.data
            binding.ivEditMyProfile.setImageURI(imageUri)
        }
    }

    // Intent로 갤러리에 접근
    fun openGallery(activity: Activity, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, requestCode)
    }

    fun uriToString(uri: Uri): String {
        return uri.toString()
    }

    // 이메일 유효성 검사 함수
    private fun isRegularEmail(): Boolean {
        val email = binding.etMyEmail.text.toString().trim()
        val pattern = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        return pattern
    }

    private fun isRegularName(): Boolean {
        val phone = binding.etMyPhoneNumber.text.toString().trim()
        val phonePattern =
            "^\\s*(010|011|012|013|014|015|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$"
        val pattern = Pattern.matches(phonePattern, phone) // 패턴이 맞는지 확인

        if (pattern) {
            return true
        } else {

            return false
        }
    }

}