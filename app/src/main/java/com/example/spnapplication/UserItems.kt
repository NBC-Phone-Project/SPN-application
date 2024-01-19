package com.example.spnapplication

import android.net.Uri


sealed interface UserItems {
    data class UserInfo(
        var aUserImage: Int,
        var aUserName: String,
        var aUserNumber: String,
        var aUserEmail: String,
        var aUserMemo: String,
        var aIsLike: Boolean,
        var aprofileImage: Uri? = null,
    ) : UserItems

    data class UserTitle(var aTitle: String) : UserItems
}