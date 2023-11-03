package com.example.branchtask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchtask.model.LoginBody
import com.example.branchtask.model.LoginResponse
import com.example.branchtask.repo.LoginRepo
import com.example.branchtask.util.Resource
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repo:LoginRepo): ViewModel(){
    private val _loginData  = MutableLiveData<Resource<LoginResponse>>()
    val loginData : LiveData<Resource<LoginResponse>> = _loginData

    fun login(data:LoginBody)
    {
            viewModelScope.launch(Dispatchers.IO) {
                when(val data = repo.getLogin(data))
                {
                    is ApiResponse.Failure ->{_loginData.postValue(Resource.Error(data.message().toString()))}
                    is ApiResponse.Success ->{_loginData.postValue(Resource.Success(data.data))}
                }
            }
    }
}