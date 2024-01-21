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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spnapplication.const.DummyUserInfo
import com.example.spnapplication.const.IntentKeys.USER_INFO
import com.example.spnapplication.contactDetail.ContactDetailActivity
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

        val userList = DummyUserInfo.DummyData

        binding?.ibContactGoToAddContact?.setOnClickListener {
            val dialogFragment = DialogAddItemFragment()
            dialogFragment.show(childFragmentManager, "ContactFragment")
        }


        val adapter = UserAdapter(userList)
        val lLayoutManager = LinearLayoutManager(context)
        binding?.rvContactRecyclerView?.adapter = adapter
        binding?.rvContactRecyclerView?.layoutManager = lLayoutManager


        // 리스트 뷰 아이콘 클릭 시 리스트 뷰 <-> 그리드 뷰 전환
        binding?.ibMainViewChange?.setOnClickListener {
            if (binding?.rvContactRecyclerView?.layoutManager == lLayoutManager){
                binding?.rvContactRecyclerView?.layoutManager = GridLayoutManager(context,2)
            } else {
                binding?.rvContactRecyclerView?.layoutManager = lLayoutManager
            }
        }

        val swipeHelperCallback = SwipeHelperCallback(adapter).apply {
            // 스와이프한 뒤 고정시킬 위치 지정
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding?.rvContactRecyclerView)

        //상품 사이에 회색 줄 추가
        val decoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        binding?.rvContactRecyclerView?.addItemDecoration(decoration)

        // StickyHeader (리사이클러 뷰 스크롤 이동 시 타이틀 고정 효과) 적용
//        binding?.rvContactRecyclerView?.addItemDecoration(StickyHeader(
//            binding?.rvContactRecyclerView as RecyclerView
//        ) { itemPosition: Int ->
//            userList[itemPosition] is UserItems.UserTitle
//        })

        // 통화 아이콘 클릭 시 CALL 액션 및 Intent로 전화번호 데이터 전달
        adapter.itemClick = object : UserAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                // 선택된 유저
                val selectedUser = userList[position] as UserInfo
                // Intent로 전화걸기
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${selectedUser.phoneNumber}")
                startActivity(intent)
            }
        }

        // DetailActivity로 데이터 전달 및 화면 전환
        adapter.goToDetail = object : UserAdapter.GoToDetail {
            override fun onGoToDetail(view: View, position: Int) {
                // 선택된 유저
                val chooseUser = userList[position]
                // Intent로 화면 전환, li 데이터전달
                val intent = Intent(activity, ContactDetailActivity::class.java)
                intent.putExtra(USER_INFO, chooseUser)
                activity?.startActivity(intent)
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

        return binding?.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemAdded(item: UserInfo) {
        addItem(item)
    }

    private fun addItem(item: UserInfo) {
        val adapter = recyclerView.adapter as UserAdapter
        adapter.addContact(item)
        adapter.notifyItemInserted(adapter.itemCount - 1)
    }

    override fun onResume() {
        super.onResume()
        binding?.rvContactRecyclerView?.adapter?.notifyDataSetChanged()
    }
}

