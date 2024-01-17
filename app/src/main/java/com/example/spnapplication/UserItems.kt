package com.example.spnapplication

sealed interface UserItems {
    data class UserInfo(
        var aUserImage: Int,
        var aUserName: String,
        var aUserNumber: String,
        var aUserEmail: String,
        var aUserMemo: String
    ) : UserItems

    data class UserTitle(var aTitle: String) : UserItems
}