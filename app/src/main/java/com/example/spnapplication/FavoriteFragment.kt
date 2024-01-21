package com.example.spnapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spnapplication.const.DummyUserInfo
import com.example.spnapplication.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        recyclerView = binding!!.rvContactRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        /* filter 함수로 DummyData의 isLike가 True이면 True인 데이터만 출력 */
        val userList = DummyUserInfo.DummyData.filter { it.isLike }.toMutableList()

        val adapter = UserAdapter(userList)
        binding?.rvContactRecyclerView?.adapter = adapter
        binding?.rvContactRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val swipeHelperCallback = SwipeHelperCallback(adapter).apply {
            // 스와이프한 뒤 고정시킬 위치 지정
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding?.rvContactRecyclerView)

        //상품 사이에 회색 줄 추가
        val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        binding?.rvContactRecyclerView?.addItemDecoration(decoration)

        // 통화 아이콘 클릭 시 CALL 액션 및 Intent로 전화번호 데이터 전달
        adapter.itemClick = object : UserAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                // 선택된 유저
                val selectedUser = userList[position] as UserInfo
                // Intent로 전화걸기
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${selectedUser.userNumber}")
                startActivity(intent)
            }
        }
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

        binding?.ivContactSearch?.setOnClickListener {
            adapter.search(binding?.etContactSearch?.text.toString())
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}