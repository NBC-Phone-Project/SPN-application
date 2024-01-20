package com.example.spnapplication.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    private const val YMD_FORMAT = "yyyy년 MM월 dd일"
    private const val HM_FORMAT = "a HH:mm"

    fun LocalDateTime.formatToYMD(): String {
        val formatter = DateTimeFormatter.ofPattern(YMD_FORMAT)
        return this.format(formatter)
    }

    fun LocalDateTime.formatToHM(): String {
        val formatter = DateTimeFormatter.ofPattern(HM_FORMAT)
        return this.format(formatter)
    }
}