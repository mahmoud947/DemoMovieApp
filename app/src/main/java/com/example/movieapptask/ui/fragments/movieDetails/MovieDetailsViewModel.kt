package com.example.movieapptask.ui.fragments.movieDetails

import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.utils.Resource
import com.example.domain.models.Movie
import com.example.domain.usecases.RefreshMoviesByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMoviesByIdUseCase: RefreshMoviesByIdUseCase
):BaseViewModel() {
    private val _movie = MutableStateFlow<Resource<Movie>>(Resource.Loading)
    val movie: StateFlow<Resource<Movie>> get() = _movie

    fun getMovieById(id:Int){
        launchCoroutine(Dispatchers.IO) {
            getMoviesByIdUseCase(id).onEach { _movie.emit(it) }.launchIn(viewModelScope)
        }
    }
}