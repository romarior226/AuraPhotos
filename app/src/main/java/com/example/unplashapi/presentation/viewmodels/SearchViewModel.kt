package com.example.unplashapi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unplashapi.domain.models.ResultState
import com.example.unplashapi.domain.models.SimplePhoto
import com.example.unplashapi.domain.usecases.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase
) : ViewModel() {
    private val _searchState =
        MutableStateFlow<ResultState<List<SimplePhoto>>>(ResultState.Success(emptyList()))
    val searchState: StateFlow<ResultState<List<SimplePhoto>>> = _searchState
    private val _query = MutableStateFlow("")

    private var currentQuery = ""

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    init {
        viewModelScope.launch {
            _query
                .debounce(500)
                .filter { it.isNotBlank() }
                .collect { query ->
                    currentQuery = query
                    _searchState.value = ResultState.Loading
                    try {
                        _searchState.value = ResultState.Success(searchPhotosUseCase(query))
                    } catch (e: Exception) {
                        _searchState.value = ResultState.Error(e.message ?: "Unknown Error")
                    }
                }
        }
    }

    fun loadMoreSearchedPhotos(page: Int) {
        viewModelScope.launch {
            try {
                val more = searchPhotosUseCase(currentQuery, page)
                val current = (_searchState.value as? ResultState.Success)?.data ?: emptyList()
                val currentIds = current.map { it.id }.toSet()
                _searchState.value =
                    ResultState.Success(current + more.filter { it.id !in currentIds })
            } catch (e: Exception) {
                _searchState.value = ResultState.Error(e.message ?: "Unknown Error")
            }
        }
    }

}