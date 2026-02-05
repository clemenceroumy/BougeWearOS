package fr.croumy.bouge.presentation.data.converters

import androidx.room.TypeConverter
import java.util.UUID


class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String): UUID {
        return UUID.fromString(string)
    }
}