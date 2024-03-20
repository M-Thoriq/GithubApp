package com.thoriq.githubsubmit2.data.repository


import androidx.lifecycle.LiveData
import com.thoriq.githubsubmit2.data.local.FavoriteUser
import com.thoriq.githubsubmit2.data.local.UserDao
import com.thoriq.githubsubmit2.data.remote.response.DetailUserResponse
import com.thoriq.githubsubmit2.data.remote.response.GithubResponse
import com.thoriq.githubsubmit2.data.remote.response.ItemsItem
import com.thoriq.githubsubmit2.data.remote.retrofit.ApiService
import com.thoriq.githubsubmit2.ui.SettingPreferences
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository private constructor(

    private val apiService: ApiService,

    private val userDao: UserDao,

    private val settingPreferences: SettingPreferences

) {

    fun getThemeSettings(): Flow<Boolean> {

        return settingPreferences.getThemeSetting()

    }


    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {

        settingPreferences.saveThemeSetting(isDarkModeActive)

    }
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun getListUsers(query: String): GithubResponse {

        return apiService.getListUsers(query)

    }

    fun getDetailUser(query: String): Call<DetailUserResponse> = apiService.getDetailUser(query)

    fun isRowIsExist(user: String) : LiveData<Boolean> = userDao.isRowIsExist(user)

    fun getAllUsers(): LiveData<List<FavoriteUser>> = userDao.getAllUsers()

    fun getFavoriteUser(name: String): LiveData<FavoriteUser> = userDao.getFavoriteUser(name)

    fun insert(user: FavoriteUser) {
        executorService.execute{
            userDao.insert(user)
        }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute{
            userDao.delete(user)
        }
    }

    companion object {

        private const val TAG = "UserRepository"


        @Volatile

        private var instance: UserRepository? = null

        fun getInstance(

            apiService: ApiService,

            newsDao: UserDao,

            settingPreferences: SettingPreferences

            ): UserRepository =

            instance ?: synchronized(this) {

                instance ?: UserRepository(apiService, newsDao, settingPreferences)

            }.also { instance = it }

    }

}