package com.example.spnapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spnapplication.databinding.FragmentContactBinding

class ContactFragment : Fragment(), OnItemAddedListener {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        recyclerView = binding!!.rvContactRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val userList = mutableListOf(
            UserItems.UserTitle("ㄱ"),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserTitle("ㄴ"),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserTitle("ㄷ"),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
            UserItems.UserInfo(
                R.mipmap.ic_launcher,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com"
            ),
        )

        binding?.ibContactGoToAddContact?.setOnClickListener {
            val dialogFragment = DialogAddItemFragment()
            dialogFragment.show(childFragmentManager, "ContactFragment")
        }


        val adapter = UserAdapter(userList)
        binding?.rvContactRecyclerView?.adapter = adapter
        binding?.rvContactRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        return binding?.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemAdded(item: UserItems) {
        addItem(item)
    }

    private fun addItem(item: UserItems) {
        val adapter = recyclerView.adapter as UserAdapter
        adapter.addContact(item)
        adapter.notifyItemInserted(adapter.itemCount - 1)
    }
}

