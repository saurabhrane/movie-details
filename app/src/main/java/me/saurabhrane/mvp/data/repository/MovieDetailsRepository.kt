package me.saurabhrane.mvp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import me.saurabhrane.mvp.data.remote.GetCastAndCrewResponse
import me.saurabhrane.mvp.data.remote.GetMovieListResponse
import me.saurabhrane.mvp.network.MoviesApiService
import retrofit2.Response
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieDetailsRepository @Inject constructor(
    val moviesService: MoviesApiService
) {

    fun getSimilarMovies(movieId: String): Flow<Resource<GetMovieListResponse>> {
        return object : NetworkResource<GetMovieListResponse>(){
            override suspend fun fetchFromRemote(): Response<GetMovieListResponse> {
                return moviesService.getSimilarMovie(movieId)
            }
        }.asFlow()
    }


    fun getCrewAndCast(movieId: String) : Flow<Resource<GetCastAndCrewResponse>> {
        return object : NetworkResource<GetCastAndCrewResponse>(){
            override suspend fun fetchFromRemote(): Response<GetCastAndCrewResponse> {
                return moviesService.getMovieCredits(movieId)
            }

        }.asFlow()
    }


}