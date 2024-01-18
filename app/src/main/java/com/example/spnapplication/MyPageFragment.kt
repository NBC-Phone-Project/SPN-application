package com.example.spnapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.spnapplication.databinding.FragmentMyPageBinding

private const val ARG_PRAM1 = "param1"
private const val ARG_PRAM2 = "param2"
private const val ARG_PRAM3 = "param3"
class MyPageFragment : Fragment() {
    private val binding by lazy { FragmentMyPageBinding.inflate(layoutInflater) }
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PRAM1)
            param2 = it.getString(ARG_PRAM2)
            param3 = it.getString(ARG_PRAM3)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMyPhoneNumberData.text = param1
        binding.tvMyEailData.text = param2
        binding.tvMyMemoData.text = param3
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRAM1, param1)
                    putString(ARG_PRAM2, param2)
                    putString(ARG_PRAM3, param3)
                }
            }
    }
}