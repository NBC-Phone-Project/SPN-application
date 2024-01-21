package com.example.spnapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.spnapplication.databinding.FragmentContactItemRecyclerviewBinding
import com.example.spnapplication.databinding.FragmentContactItemTitleBinding
import java.util.Collections

class UserAdapter(val mItems: MutableList<UserInfo>) : RecyclerView.Adapter<ViewHolder>() {

    private var mItemsCopyList: MutableList<UserInfo> = mItems.map { it.copy() }.toMutableList()

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    interface GoToDetail {
        fun onGoToDetail(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    var goToDetail: GoToDetail? = null

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
        val userImageUri = binding.ivContactUserIcon
        val userName = binding.tvContactUsername
        val btnCall = binding.ivContactCall
        val remove = binding.tvRemove
        val btnGoDetail = binding.clContactGoToDetailBtn
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
        (holder as UserViewHolder).userName.text = item.name
        holder.userImage.setImageResource(item.image)
        holder.userImageUri.setImageURI(item.profileImage)
        holder.btnCall.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        // 삭제 텍스트뷰 클릭시 토스트 표시
        holder.remove.setOnClickListener {
            removeData(position)
        }
        holder.btnGoDetail.setOnClickListener {
            goToDetail?.onGoToDetail(it, position)
        }
    }


    fun removeData(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(mItems, fromPos, toPos)
        notifyItemMoved(fromPos, toPos)
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
        mItems.sortBy { it.name }
    }

    fun search(first: String) {
        val name = first

        if (name.isBlank()) {
            mItems.clear()
            mItems.addAll(mItemsCopyList)
        } else {
            val filteredList = mItems.filter { it.name.contains(name) }
            mItems.clear()
            mItems.addAll(filteredList)
        }
        notifyDataSetChanged()
    }
}
