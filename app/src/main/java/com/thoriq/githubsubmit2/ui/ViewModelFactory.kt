package com.thoriq.githubsubmit2.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoriq.githubsubmit2.data.repository.Injection
import com.thoriq.githubsubmit2.data.repository.UserRepository
import com.thoriq.githubsubmit2.ui.detail.DetailUserViewModel
import com.thoriq.githubsubmit2.ui.favorite.FavoriteViewModel
import com.thoriq.githubsubmit2.ui.main.MainViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        }
        else if (modelClass.isAssignableFrom(ModeViewModel::class.java)) {
            return ModeViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}