package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.models.PhotoStatistics
import com.example.unplashapi.domain.models.ResultState
import com.example.unplashapi.domain.usecases.GetDetailedPhotoUseCase
import com.example.unplashapi.domain.usecases.GetPhotoStatistics
import com.example.unplashapi.domain.usecases.TriggerPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getDetailedPhotoUseCase: GetDetailedPhotoUseCase,
    val triggerPostUseCase: TriggerPostUseCase,
    val getPhotoStatistics: GetPhotoStatistics
) : ViewModel() {

    private val _photoState = MutableStateFlow<ResultState<DetailPhoto>>(ResultState.Loading)
    val photoState: StateFlow<ResultState<DetailPhoto>> = _photoState

    val photoStatistics = MutableStateFlow<PhotoStatistics?>(null)

    fun loadPhoto(id: String) {
        viewModelScope.launch {
            _photoState.value = ResultState.Loading
            try {
                photoStatistics.value = getPhotoStatistics(id)
                _photoState.value = ResultState.Success(getDetailedPhotoUseCase(id))
                triggerPostUseCase(id)
            }
            catch (e: Exception ){
             _photoState.value = ResultState.Error(e.message ?: "Unknown Error")
            }

        }

    }
}