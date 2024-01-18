package com.example.spnapplication

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.spnapplication.databinding.FragmentContactItemRecyclerviewBinding
import com.example.spnapplication.databinding.FragmentContactItemTitleBinding

class UserAdapter(val mItems: MutableList<UserInfo>) : RecyclerView.Adapter<ViewHolder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

//    companion object {
//        private const val VIEW_TYPE_TITLE = 1
//        private const val VIEW_TYPE_NAME = 2
//    }

    inner class TitleViewHolder(binding: FragmentContactItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvContactUsernameTitle
    }

    inner class UserViewHolder(binding: FragmentContactItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userImage = binding.ivContactUserIcon
        val userName = binding.tvContactUsername
        val btnCall = binding.ivContactCall
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        return when (viewType) {
//            VIEW_TYPE_TITLE -> {
//                val binding = FragmentContactItemTitleBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//                TitleViewHolder(binding)
//            }
        val binding = FragmentContactItemRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
//            is UserItems.UserTitle -> {
//                (holder as TitleViewHolder).title.text = "${item.aTitle}"
//            }
        (holder as UserViewHolder).userName.text = item.userName
        holder.userImage.setImageResource(item.userImage)
        holder.btnCall.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun addContact(contact: UserInfo) {
        mItems.add(contact)
        sortList()
        notifyDataSetChanged()
    }

//    override fun getItemViewType(position: Int): Int {
//        return when (mItems[position]) {
//            is UserItems.UserTitle -> VIEW_TYPE_TITLE
//            is UserItems.UserInfo -> VIEW_TYPE_NAME
//        }
//    }

    fun sortList() {
        mItems.sortBy { it.userName }
    }

    fun search(first: String) {
        val name = first
        if (name.isBlank()){
            mItems.clear()
            mItems.addAll(mItems)
        }
        val filteredList = mItems.filter { it.userName.contains(name) }
        mItems.clear()
        mItems.addAll(filteredList)
        notifyDataSetChanged()
    }
}