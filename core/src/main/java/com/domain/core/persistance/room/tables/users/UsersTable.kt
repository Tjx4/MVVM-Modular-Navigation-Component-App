package com.domain.core.persistance.room.tables.users

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "users")
data class UsersTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,
    @ColumnInfo(name ="userName")
    var userName: String?,
    @ColumnInfo(name ="firstName")
    var firstName: String?,
    @ColumnInfo(name ="age")
    var age: Int,
    var imageThumbNail: String? = null,
    @ColumnInfo(name = "imageMedium")
    var imageMedium: String? = null,
    @ColumnInfo(name = "imageXl")
    var imageXl: String? = null
): Parcelable