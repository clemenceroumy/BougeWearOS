package fr.croumy.bouge.presentation.data.converters

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class DateConverter {
    @TypeConverter
    fun fromDateString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(value) }
    }

    @TypeConverter
    fun zonedDateTimeToString(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}