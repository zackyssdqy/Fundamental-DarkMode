package com.zacky.fundamentalsubmission.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.zacky.fundamentalsubmission.data.remote.response.GithubResponse
import com.zacky.fundamentalsubmission.data.remote.response.ItemsItem
import com.zacky.fundamentalsubmission.data.remote.retrofit.ApiConfig
import com.zacky.fundamentalsubmission.ui.SettingPreferences
import com.zacky.fundamentalsubmission.util.Event
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _userProfile = MutableLiveData<List<ItemsItem>>()
    val userProfile: LiveData<List<ItemsItem>> = _userProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    companion object{
        private const val TAG = "MainViewModel"
        private const val LOGIN = "zacky"
    }

    init{
        findGithubUser(LOGIN)
    }

    fun findGithubUser(LOGIN : String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(LOGIN)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userProfile.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

}


