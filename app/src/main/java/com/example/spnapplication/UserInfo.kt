package com.example.spnapplication

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class UserInfo(
    val userImage: Int,
    val userName: String,
    val userNumber: String,
    val userEmail: String,
    val userMemo: String,
    var isLike: Boolean,
    val mutableListOf: MutableList<LocalDateTime> = mutableListOf()
) : Parcelable

@Parcelize
data class MyInfo(
    val userImage: String?,
    val userName: String,
    val userNumber: String,
    val userEmail: String,
    val userMemo: String,
) : Parcelable

