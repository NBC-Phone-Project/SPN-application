package com.example.spnapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.spnapplication.databinding.FragmentMyPageBinding
import com.example.spnapplication.utils.Utils

class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        Log.d("MyPageFragment","onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        Log.d("MyPageFragment","onCreateView()")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.icEdit.setOnClickListener {
            activity?.let{
                val intent = Intent(context, EditMyPageActivity::class.java)
                startActivity(intent)
            }
        }
        Log.d("MyPageFragment","onViewCreated()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyPageFragment","onResume()")

        val myInfo = Utils.getPrefUser(requireContext())

        Log.d("onResume", "uri = ${myInfo?.userImage.toString()}")
        Log.d("onResume", "name = ${myInfo?.userName.toString()}")
        Log.d("onResume", "tel = ${myInfo?.userNumber.toString()}")
        Log.d("onResume", "email = ${myInfo?.userEmail.toString()}")
        Log.d("onResume", "memo = ${myInfo?.userMemo.toString()}")

        if (myInfo != null) {
            try {

                Glide.with(requireContext())
                    .load(myInfo.userImage?.let { stringToUri(it) })
                    .into(binding.ivMyProfile)

//                binding.ivMyProfile.setImageURI(myInfo.userImage?.let { stringToUri(it) })
            } catch (e: Exception) {
                Log.e("MyPageFragment", "Error setting image URI", e)
            }

            binding.tvMyname.text = myInfo.userName
            binding.tvMyPhoneNumberData.text = myInfo.userNumber
            binding.tvMyEailData.text = myInfo.userEmail
            binding.tvMyMemoData.text = myInfo.userMemo

        }else{
            val ad = AlertDialog.Builder(requireContext())
            ad.setIcon(R.drawable.iv_mypage_myprofile)
            ad.setTitle("처음이네요")
            ad.setMessage("처음이면 프로필 등록해")

            ad.setPositiveButton("확인") { dialog, _ ->
                val intent = Intent(context, EditMyPageActivity::class.java)
                startActivity(intent)
            }
            ad.setNegativeButton("취소"){ dialog,_ ->
                dialog.dismiss()
            }
            ad.show()
        }
    }

    fun stringToUri(uriString: String): Uri {
        return Uri.parse(uriString)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}