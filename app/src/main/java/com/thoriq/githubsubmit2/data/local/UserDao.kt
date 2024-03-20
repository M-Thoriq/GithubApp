package com.thoriq.githubsubmit2.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: FavoriteUser)
    @Delete
    fun delete(note: FavoriteUser)
    @Query("SELECT * from favoriteuser")
    fun getAllUsers(): LiveData<List<FavoriteUser>>
    @Query("SELECT * FROM FavoriteUser WHERE id = :username")
    fun getFavoriteUser(username: String): LiveData<FavoriteUser>
    @Query("SELECT EXISTS(SELECT * FROM favoriteuser WHERE id = :user)")
    fun isRowIsExist(user: String) : LiveData<Boolean>
}