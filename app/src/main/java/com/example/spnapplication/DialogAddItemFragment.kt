package com.example.spnapplication

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.spnapplication.databinding.FragmentDialogAddItemBinding


class DialogAddItemFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogAddItemBinding
    private var profileImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val inflater = requireActivity().layoutInflater

        binding = FragmentDialogAddItemBinding.inflate(inflater)
        dialog.setContentView(binding.root)

        binding.btnSaveDialog.setOnClickListener {
            onSaveClicked()
        }
        binding.btnCancelDialog.setOnClickListener {
            dismiss()
        }

        val dialogName = binding.nameDialogLayout
        val dialogCall = binding.callDialogLayout
        val dialogEmail = binding.emailDialogLayout
        dialog.window?.setLayout(1200, 2000)

        binding.etNameDialog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nameValid = s.toString()
                if (!NameCondition(nameValid)) {
                    dialogName.error = "이름을 입력하세요"
                } else {
                    dialogName.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etCallDialog.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        binding.etCallDialog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val callValid = s.toString()
                if (!callCondition(callValid)) {
                    dialogCall.error = "전화번호를 입력하세요"
                } else {
                    dialogCall.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.etEmailDialog.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailValid = s.toString()
                if (!emailCondition(emailValid)) {
                    dialogEmail.error = "이메일을 입력하세요"
                } else {
                    dialogEmail.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnUserInfoChangeProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        return dialog
    }

    private fun onSaveClicked() {
        val name = binding.etNameDialog.text.toString()
        val call = binding.etCallDialog.text.toString()
        val email = binding.etEmailDialog.text.toString()
        val uri = profileImageUri

        if (name.isNotEmpty() && call.isNotEmpty() && email.isNotEmpty()) {
            val item =
                UserInfo(R.drawable.img_default_profile, name, call, email, "", false, uri)
            val parentFragment = parentFragment
            if (parentFragment is OnItemAddedListener) {
                parentFragment.onItemAdded(item)
            }
            dismiss()
        }


    }

    private val activityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                profileImageUri = it.data!!.data

                Glide.with(this)
                    .load(profileImageUri)
                    .into(binding.imgAddDialog)
            }
        }
}

private fun NameCondition(name: String): Boolean {
    return name.length >= 2
}

private fun callCondition(call: String): Boolean {
    return call.length >= 13
}

private fun emailCondition(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

