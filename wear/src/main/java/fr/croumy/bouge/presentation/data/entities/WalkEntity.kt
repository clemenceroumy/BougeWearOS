package fr.croumy.bouge.presentation.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import java.time.ZonedDateTime

@Entity(tableName = "walks")
data class WalkEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "start_timestamp") val start: ZonedDateTime,
    @ColumnInfo(name = "end_timestamp") val end: ZonedDateTime,
    @ColumnInfo(name = "steps") val steps: Int,
)