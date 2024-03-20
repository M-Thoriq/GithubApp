package com.thoriq.githubsubmit2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoriq.githubsubmit2.data.local.FavoriteUser
import com.thoriq.githubsubmit2.data.remote.response.DetailUserResponse
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.data.remote.retrofit.ApiConfig
import com.thoriq.githubsubmit2.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _listReview = MutableLiveData<DetailUserResponse>()
    val listReview: LiveData<DetailUserResponse> = _listReview

    private val _listFollowing = MutableLiveData<List<ItemsItem?>>()
    val listFollowing : LiveData<List<ItemsItem?>> = _listFollowing

    private val _listFollower = MutableLiveData<List<ItemsItem?>>()
    val listFollower : LiveData<List<ItemsItem?>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var usernamefav: MutableLiveData<String>

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun getAllUsers(): LiveData<List<FavoriteUser>> = repository.getAllUsers()

    fun isRowIsExist(user: String) : LiveData<Boolean> = repository.isRowIsExist(user)

    fun getFavoriteUser(nama:String): LiveData<FavoriteUser> = repository.getFavoriteUser(nama)

    fun insert(user: FavoriteUser) {
        repository.insert(user)
    }

    fun delete(user: FavoriteUser) {
        repository.delete(user)
    }

    fun getDetailByUsername(user: String): LiveData<String> {

        usernamefav = MutableLiveData()
        usernamefav.value = user
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(user)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listReview.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return usernamefav
    }

    fun getFollowing(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollower(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(query)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
