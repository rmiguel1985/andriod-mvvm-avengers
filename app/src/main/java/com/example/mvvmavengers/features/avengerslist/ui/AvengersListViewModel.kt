package com.example.mvvmavengers.features.avengerslist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmavengers.base.usecase.ResultAvenger
import com.example.mvvmavengers.features.avengerslist.domain.LoadAvengersListUseCaseImpl
import com.example.mvvmavengers.features.avengerslist.domain.entities.AvengersModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AvengersListViewModel(
    private val loadAvengersListUseCaseImpl: LoadAvengersListUseCaseImpl
) : ViewModel() {

    val _uiState: MutableLiveData<ResultAvenger<AvengersModel>> = MutableLiveData()
    val uiState: LiveData<ResultAvenger<AvengersModel>> = _uiState

    init {
        _uiState.postValue(ResultAvenger.Loading)
        getAvengers()
    }

    fun getAvengers() {
        viewModelScope.launch {
            _uiState.value = loadAvengersListUseCaseImpl(params = Any())
        }
    }
}
