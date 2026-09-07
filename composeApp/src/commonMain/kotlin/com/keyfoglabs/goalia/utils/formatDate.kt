package com.keyfoglabs.goalia.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun formatDate(utc: String): String {
    val instant = Instant.parse(utc)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = local.dayOfMonth.toString().padStart(2, '0')
    val month = local.monthNumber.toString().padStart(2, '0')
    val hour = local.hour.toString().padStart(2, '0')
    val minute = local.minute.toString().padStart(2, '0')

    return "$day.$month • $hour:$minute"
}
