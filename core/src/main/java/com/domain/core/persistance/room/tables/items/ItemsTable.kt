package com.domain.core.persistance.room.tables.items

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "items")
data class ItemsTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,
    @ColumnInfo(name ="itemName")
    var itemName: String?,

): Parcelable