package com.example.spnapplication

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class UserInfo(
    val image: Int,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val memo: String,
    var isLike: Boolean,
    var profileImage: Uri? = null,
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

