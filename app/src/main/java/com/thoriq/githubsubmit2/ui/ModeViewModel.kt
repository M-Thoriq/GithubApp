package com.thoriq.githubsubmit2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thoriq.githubsubmit2.data.repository.UserRepository
import kotlinx.coroutines.launch

class ModeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return userRepository.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}