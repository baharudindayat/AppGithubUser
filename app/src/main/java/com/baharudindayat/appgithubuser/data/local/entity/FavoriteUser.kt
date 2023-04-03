package com.baharudindayat.appgithubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite")
class FavoriteUser (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "username")
    var login: String,

    @ColumnInfo(name = "avatar")
    var avatar_url: String
): Parcelable
