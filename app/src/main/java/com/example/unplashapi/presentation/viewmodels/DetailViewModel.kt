package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.DetailPhoto
import com.example.unplashapi.domain.usecases.GetDetailedPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val getDetailedPhotoUseCase: GetDetailedPhotoUseCase
) : ViewModel()  {

    private val _photo = MutableStateFlow<DetailPhoto?>(null)
    val photo: StateFlow<DetailPhoto?> = _photo

    fun loadPhoto(id: String) {
        viewModelScope.launch {
            _photo.value = getDetailedPhotoUseCase(id)
        }
    }
}