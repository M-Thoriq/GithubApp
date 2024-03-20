package com.thoriq.githubsubmit2.data.repository

import android.content.Context
import com.thoriq.githubsubmit2.data.local.UserDatabase
import com.thoriq.githubsubmit2.data.remote.retrofit.ApiConfig
import com.thoriq.githubsubmit2.ui.SettingPreferences
import com.thoriq.githubsubmit2.ui.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getDatabase(context)
        val dao = database.userDao()
//        val appExecutors = AppExecutors()
        val pref = SettingPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(apiService, dao, pref)
    }
}