package com.example.spnapplication.recentRecords

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spnapplication.ContactDetailActivity
import com.example.spnapplication.UserInfo
import com.example.spnapplication.const.DummyUserInfo
import com.example.spnapplication.databinding.FragmentRecentRecordsBinding
import com.example.spnapplication.ui.HeaderItemDecoration
import com.example.spnapplication.utils.Utils.formatToYMD
import java.time.LocalDateTime

class RecentRecordsFragment : Fragment(), CallAdapter.ItemClick {

    private var fragmentBinding: FragmentRecentRecordsBinding? = null
    private val binding get() = fragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        fragmentBinding = FragmentRecentRecordsBinding.inflate(inflater, container, false)
        val rootView: View = binding.root

        initializeViews()

        return rootView
    }

    private fun initializeViews() {
        // RecyclerView
        val callGroups = convertUserDataToCallGroups()

        binding.rvRecentRecords.adapter = CallAdapter(callGroups).apply { itemClick = this@RecentRecordsFragment }
        binding.rvRecentRecords.layoutManager = LinearLayoutManager(context)

        binding.rvRecentRecords.addItemDecoration(HeaderItemDecoration(binding.rvRecentRecords) { itemPosition: Int -> callGroups[itemPosition] is RecentCalls.CallHeader })
        binding.rvRecentRecords.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL
            )
        )
    }

    private fun convertUserDataToCallGroups(): MutableList<RecentCalls> {
        val callGroups = CallGroups(mutableListOf())

        DummyUserInfo.DummyData.forEach { userData ->
            userData.mutableListOf.forEach { recentCallTime ->
                val foundGroup =
                    callGroups.callGroups.find { it.time.isSameYearMonthDay(recentCallTime) }

                if (foundGroup != null) {
                    foundGroup.calls.add(
                        RecentCalls.CallItem(
                            userData.name, userData.phoneNumber, recentCallTime
                        )
                    )
                } else {
                    val newCallGroup = CallGroup(
                        recentCallTime, mutableListOf(
                            RecentCalls.CallItem(
                                userData.name, userData.phoneNumber, recentCallTime
                            )
                        )
                    )
                    callGroups.callGroups.add(newCallGroup)
                }
            }
        }

        return callGroups.sortAndCopy()
    }

    private fun CallGroups.sortAndCopy(): MutableList<RecentCalls> {
        val sortedCallGroups = callGroups.sortedByDescending { it.time }
            .map { callGroup ->
                val sortedCalls = callGroup.calls.sortedByDescending { it.time }
                CallGroup(callGroup.time, sortedCalls.toMutableList())
            }
            .toMutableList()

        return convertToMutableList(CallGroups(sortedCallGroups))
    }

    private fun convertToMutableList(callGroups: CallGroups): MutableList<RecentCalls> {
        val resultList: MutableList<RecentCalls> = mutableListOf()

        for (callGroup in callGroups.callGroups) {
            resultList.add(RecentCalls.CallHeader(callGroup.time.formatToYMD()))
            resultList.addAll(callGroup.calls.map { call ->
                RecentCalls.CallItem(call.name, call.phoneNumber, call.time)
            })
        }

        return resultList
    }

    private fun LocalDateTime.isSameYearMonthDay(dateTime: LocalDateTime): Boolean {
        return this.toLocalDate().compareTo(dateTime.toLocalDate()) == 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding = null
    }

    override fun onClick(callItem: RecentCalls.CallItem) {
        Log.d("Recent", "click!!")
        val foundUserInfo = DummyUserInfo.findItemByNameAndPhoneNumber(callItem.name, callItem.phoneNumber)
        startActivity(
            Intent(context, ContactDetailActivity::class.java).apply {
                putExtra("UserInfo", foundUserInfo)
            }
        )
    }
}
