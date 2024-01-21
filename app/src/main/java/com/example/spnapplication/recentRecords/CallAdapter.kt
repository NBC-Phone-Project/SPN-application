package com.example.spnapplication.recentRecords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spnapplication.R
import com.example.spnapplication.databinding.ItemRecentRecordsContentBinding
import com.example.spnapplication.databinding.ItemRecentRecordsStickyHeaderBinding
import com.example.spnapplication.utils.Utils.formatToHM

class CallAdapter(private val callGroups: MutableList<RecentCalls>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClick {
        fun onClick(view: RecentCalls.CallItem)
    }

    var itemClick: ItemClick? = null

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(
                ItemRecentRecordsStickyHeaderBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_ITEM -> ContentViewHolder(
                ItemRecentRecordsContentBinding.inflate(inflater, parent, false)
            )

            else -> error("Unhandled viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = callGroups[position]) {
            is RecentCalls.CallHeader -> (holder as HeaderViewHolder).bind(item)
            is RecentCalls.CallItem -> (holder as ContentViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (callGroups[position]) {
            is RecentCalls.CallHeader -> VIEW_TYPE_HEADER
            is RecentCalls.CallItem -> VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = callGroups.size

    inner class HeaderViewHolder(private val binding: ItemRecentRecordsStickyHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: RecentCalls.CallHeader) {
            binding.tvTitle.text = header.title
        }
    }

    inner class ContentViewHolder(private val binding: ItemRecentRecordsContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentCalls.CallItem) {
            with(binding) {
                name.text = item.name
                time.text = item.time.formatToHM()
                ivCallState.setImageResource(if(item.isCallReceived) R.drawable.ic_recent_records_call_received_24dp else R.drawable.ic_recent_records_call_made_24dp)

                itemView.setOnClickListener {
                    itemClick?.onClick(item)
                }
            }
        }
    }

}


