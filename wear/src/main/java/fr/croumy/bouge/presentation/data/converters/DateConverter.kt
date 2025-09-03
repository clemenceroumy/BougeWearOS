package fr.croumy.bouge.presentation.data.converters

import androidx.room.TypeConverter
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Instant

class DateConverter {
    @TypeConverter
    fun fromDateTimeString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(value) }
    }

    @TypeConverter
    fun zonedDateTimeToString(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}