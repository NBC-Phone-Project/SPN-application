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

        binding.run {
            icMypageBack.setOnClickListener {
                val myPhoneNumber = binding.etMyPhoneNumber.text.toString()
                val myEmail = binding.etMyEmail.text.toString()
                val myMemo = binding.etMyMemo.text.toString()
                val data = MyPageFragment.newInstance(myPhoneNumber,myEmail,myMemo)
                setFragment(data)
            }
        }
    }

    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.vp_viewpager_main, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}