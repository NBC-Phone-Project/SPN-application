package com.example.spnapplication

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    val userImage: Int,
    val userName: String,
    val userNumber: String,
    val userEmail: String,
    val userMemo: String,
    var isLike: Boolean,
    var profileImage: Uri? = null,
) : Parcelable
