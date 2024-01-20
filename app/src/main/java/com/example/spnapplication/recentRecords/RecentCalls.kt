package com.example.spnapplication.recentRecords

import java.time.LocalDateTime
import kotlin.random.Random

sealed class RecentCalls {
    data class CallHeader(
        val title: String,
    ) : RecentCalls()

    data class CallItem(
        val name: String,
        val phoneNumber: String,
        val time: LocalDateTime,
        val isCallReceived: Boolean = Random.nextBoolean() // 전화 수신/발신 상태를 랜덤하게 정함
    ) : RecentCalls()
}

data class CallGroup(val time: LocalDateTime, val calls: MutableList<RecentCalls.CallItem>)

data class CallGroups(val callGroups: MutableList<CallGroup>)




