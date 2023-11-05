package com.example.branchtask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchtask.model.GetHomeResponse
import com.example.branchtask.model.LoginBody
import com.example.branchtask.model.LoginResponse
import com.example.branchtask.repo.HomeRepo
import com.example.branchtask.repo.LoginRepo
import com.example.branchtask.util.Resource
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: HomeRepo): ViewModel() {
    private val _homeData = MutableLiveData<Resource<List<GetHomeResponse>>>()
    val homeData: LiveData<Resource<List<GetHomeResponse>>> = _homeData
    private val _postData = MutableLiveData<Resource<GetHomeResponse>>()
    val postData: LiveData<Resource<GetHomeResponse>> = _postData
    private val _resetData = MutableLiveData<Resource<ResponseBody>>()
    val resetData: LiveData<Resource<ResponseBody>> = _resetData
    val _sortedData = MutableLiveData<List<GetHomeResponse>>()

    //    val sortedData:LiveData<Map<String,GetHomeResponse>> g
    fun home() {

        viewModelScope.launch(Dispatchers.IO) {
            _homeData.postValue(Resource.Loading())
            when (val data = repo.getHome()) {
                is ApiResponse.Failure -> {
                    _homeData.postValue(Resource.Error(data.message().toString()))
                }

                is ApiResponse.Success -> {
                    Log.d("adsasdass",data.data.toString())
                    _homeData.postValue(Resource.Success(data.data))
                }
            }
        }
    }
    
    fun postMsg(id: String, body: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val data = repo.postMessage(id = id, body = body)) {
                is ApiResponse.Failure -> {
                    _postData.postValue(Resource.Error(data.message().toString()))
                }

                is ApiResponse.Success -> {
                    val currentData = _sortedData.value
                        ?: emptyList() // Get the current data or an empty list if it's null
                    val updatedData = currentData + data.data
                    _sortedData.postValue(updatedData)
                    _postData.postValue(Resource.Success(data.data))
                }
            }
        }
    }

    fun getSortedData(data: List<GetHomeResponse>): Map<String, List<GetHomeResponse>> {
        var groupedData = data.groupBy { it.thread_id }
        val sortedMessages = groupedData.mapValues { (_, messages) ->
            messages.sortedByDescending { it.timestamp }
        }
//        _sortedData.postValue(sortedMessages)
        return sortedMessages
    }

    fun reset() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val data = repo.reset()) {
                is ApiResponse.Failure -> {
//                    Log.d("asdasdsa",data.message().toString())
                    _resetData.postValue(Resource.Error(data.message().toString()))
                }

                is ApiResponse.Success -> {
//                    Log.d("asdasdsa","Happ")
                    _sortedData.postValue(emptyList())
                    _resetData.postValue(Resource.Success(data.data))

                }
            }
        }
    }
}