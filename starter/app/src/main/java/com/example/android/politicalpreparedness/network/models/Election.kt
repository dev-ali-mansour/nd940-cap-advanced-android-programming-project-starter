package com.example.android.politicalpreparedness.network.models

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "elections")
@Parcelize
data class Election(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @ColumnInfo(name = "isSaved") var isSaved: Boolean = false,
    @Embedded(prefix = "division_") @Json(name = "ocdDivisionId") val division: Division
):Parcelable