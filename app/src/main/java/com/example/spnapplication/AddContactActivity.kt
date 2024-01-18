package com.example.spnapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.spnapplication.databinding.ActivityAddContactBinding
import com.example.spnapplication.databinding.ActivityMainBinding

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding
    private lateinit var adapter: UserAdapter
    private val fragmentManager = supportFragmentManager
    private lateinit var transaction: FragmentTransaction
    private val data = mutableListOf<UserItems>()
    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.vp_viewpager_main, ContactFragment())

        binding.btnSave.setOnClickListener{
            val bundle = Bundle()
            val name = binding.etName.toString()
            val phoneNumber = binding.etPhonenumber.toString()
            val email = binding.etEmail.toString()

            bundle.putString("name",name)
            bundle.putString("phoneNumber",phoneNumber)
            bundle.putString("email",email)
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("name",name)
                Log.d(TAG,"name: ${name}")

            }

            startActivity(intent)
        }
        binding.btnCancel.setOnClickListener{
            finish()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}