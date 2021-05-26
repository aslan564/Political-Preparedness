package com.example.android.politicalpreparedness.network.models.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.util.*
@Parcelize
@Entity(tableName = "election_table")
data class Election(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") val division: Division
):Parcelable

