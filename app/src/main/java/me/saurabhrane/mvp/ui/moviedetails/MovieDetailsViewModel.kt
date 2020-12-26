package me.saurabhrane.mvp.ui.moviedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.saurabhrane.mvp.data.local.Movie
import me.saurabhrane.mvp.data.remote.GetCastAndCrewResponse
import me.saurabhrane.mvp.data.remote.GetMovieListResponse
import me.saurabhrane.mvp.data.repository.MovieDetailsRepository
import me.saurabhrane.mvp.data.repository.Resource

@ExperimentalCoroutinesApi
class MovieDetailsViewModel @ViewModelInject constructor(
    val repository: MovieDetailsRepository
) : ViewModel() {

    val movie = MutableLiveData<Movie>()
    private val _similarMoviesLiveData = MutableLiveData<Resource<GetMovieListResponse>>()
    val similarMoviesLiveData: LiveData<Resource<GetMovieListResponse>>
        get() = _similarMoviesLiveData

    private val _castLiveData = MutableLiveData<Resource<GetCastAndCrewResponse>>()
    val castLiveData: LiveData<Resource<GetCastAndCrewResponse>>
        get() = _castLiveData

    /**
     * Loads Cast and Crew from the Api
     * @param movieId is the id of the movie whose details are to be loaded
     */
    fun loadCastAndCrew(movieId: String) {
        viewModelScope.launch {
            repository.getCrewAndCast(movieId).collect {
                _castLiveData.value = it
            }
        }
    }

    /**
     * Loads Similar Movies from the Api
     * @param movieId is the id of the movie whose details are to be loaded
     */
    fun loadSimilarMovie(movieId: String) {
        viewModelScope.launch {
            repository.getSimilarMovies(movieId).collect {
                _similarMoviesLiveData.value = it
            }
        }
    }

}