package com.example.spnapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
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


        // 플로팅 버튼
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true
        binding?.rvContactRecyclerView?.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding?.rvContactRecyclerView?.canScrollVertically(-1) == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding?.fbContactFloating?.startAnimation(fadeOut)
                    binding?.fbContactFloating?.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop){
                        binding?.fbContactFloating?.visibility = View.VISIBLE
                        binding?.fbContactFloating?.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })
        binding?.fbContactFloating?.setOnClickListener {
            binding?.rvContactRecyclerView?.smoothScrollToPosition(0)
        }

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

