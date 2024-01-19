package com.example.spnapplication

import java.time.LocalDateTime

sealed class UserItems {
    data class UserInfo(
        var aUserImage: Int,
        var aUserName: String,
        var aUserNumber: String,
        var aUserEmail: String,
        var aUserMemo: String,
        var aIsLike: Boolean,
        var aRecentRecordsDateTime: MutableList<LocalDateTime> = mutableListOf()
    ) : UserItems()

    data class UserTitle(var aTitle: String) : UserItems()
}