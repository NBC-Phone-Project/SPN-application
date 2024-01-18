package com.example.spnapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spnapplication.databinding.FragmentContactBinding

class ContactFragment : Fragment() {

    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactBinding.inflate(inflater, container, false)

        val userList = mutableListOf(
            UserItems.UserTitle("ㄱ"),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "김철수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserTitle("ㄴ"),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "노민수",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserTitle("ㄷ"),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
            UserItems.UserInfo(
                R.drawable.iv_mypage_myprofile,
                "도기백",
                "010-1111-2222",
                "CSKim@naver.com",
                "CSKim@naver.com",
                false
            ),
        )

        val adapter = UserAdapter(userList)
        binding?.rvContactRecyclerView?.adapter = adapter
        binding?.rvContactRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        // StickyHeader (리사이클러 뷰 스크롤 이동 시 타이틀 고정 효과) 적용
        binding?.rvContactRecyclerView?.addItemDecoration(StickyHeader(
            binding?.rvContactRecyclerView as RecyclerView
        ) { itemPosition: Int ->
            userList[itemPosition] is UserItems.UserTitle
        })


        // 플로팅 버튼
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500 }
        var isTop = true
        binding?.rvContactRecyclerView?.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding?.rvContactRecyclerView?.canScrollVertically(-1) == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding?.fbContactFloating?.startAnimation(fadeOut)
                    binding?.fbContactFloating?.visibility = View.GONE
                    isTop = true
                } else {
                    if (isTop) {
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
}
