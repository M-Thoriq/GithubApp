package com.thoriq.githubsubmit2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.data.repository.UserRepository
import com.thoriq.githubsubmit2.ui.detail.UserUiState
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState<List<ItemsItem?>>>()
    val uiState: LiveData<UserUiState<List<ItemsItem?>>> = _uiState

    private val _listReview = MutableLiveData<List<ItemsItem?>>()
    val listReview: LiveData<List<ItemsItem?>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_ID = "arif"
    }

    init{
        searchUser(USER_ID)
    }

    fun searchUser(query: String) {
        _uiState.value = UserUiState.Loading

        viewModelScope.launch {
            val client = repository.getListUsers(query)
            try {

                _uiState.value = UserUiState.Success(repository.getListUsers(query).items!!)

            } catch (e: Exception) {

                _uiState.value = UserUiState.Error(e.message.toString())

            }

        }
    }
}
