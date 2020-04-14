package com.fakher.multimodule.mvvm.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val username: String,
    @ColumnInfo(name = "password_hash")
    val passwordHash: Int,
    val info: String
) : Parcelable