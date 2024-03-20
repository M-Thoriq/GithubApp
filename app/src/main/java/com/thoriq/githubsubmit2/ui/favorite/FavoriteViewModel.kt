package com.thoriq.githubsubmit2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thoriq.githubsubmit2.data.local.FavoriteUser
import com.thoriq.githubsubmit2.data.local.UserDao
import com.thoriq.githubsubmit2.data.remote.retrofit.ApiService
import com.thoriq.githubsubmit2.data.repository.UserRepository

class FavoriteViewModel(private val repository: UserRepository): ViewModel() {

    fun getAllUsers(): LiveData<List<FavoriteUser>> = repository.getAllUsers()

}