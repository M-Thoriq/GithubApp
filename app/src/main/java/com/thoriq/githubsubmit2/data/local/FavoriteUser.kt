package com.thoriq.githubsubmit2.data.local

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class FavoriteUser(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var usrname: String = "",
    @ColumnInfo(name = "avatarUrl")
    var avatarUrl: String?=null,
)