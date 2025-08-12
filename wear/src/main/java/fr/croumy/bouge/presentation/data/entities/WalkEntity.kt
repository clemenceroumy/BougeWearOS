package fr.croumy.bouge.presentation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.time.ZonedDateTime
import java.util.UUID

@Entity(tableName = "walks")
data class WalkEntity(
    @PrimaryKey val uid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "start_timestamp") val start: ZonedDateTime,
    @ColumnInfo(name = "end_timestamp") val end: ZonedDateTime,
    @ColumnInfo(name = "steps") val steps: Int,
)