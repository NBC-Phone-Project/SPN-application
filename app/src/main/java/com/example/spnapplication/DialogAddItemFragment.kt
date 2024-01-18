package com.example.spnapplication

import android.app.Dialog
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.spnapplication.databinding.FragmentDialogAddItemBinding


class DialogAddItemFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogAddItemBinding

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

        return dialog
    }

    private fun onSaveClicked() {
        val name = binding.etNameDialog.text.toString()
        val call = binding.etCallDialog.text.toString()
        val email = binding.etEmailDialog.text.toString()

        if (name.isNotEmpty() && call.isNotEmpty() && email.isNotEmpty()) {
            val item = UserItems.UserInfo(R.drawable.img_default_profile, name, call, email, "")
            val parentFragment = parentFragment
            if (parentFragment is OnItemAddedListener) {
                parentFragment.onItemAdded(item)
            }
            dismiss()
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

