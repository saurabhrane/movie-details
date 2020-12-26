package me.saurabhrane.mvp.mapper

import me.saurabhrane.mvp.data.remote.GetMovieListResponse
import me.saurabhrane.mvp.data.repository.NetworkResponseMapper

class MoviesResponseMapper : NetworkResponseMapper<GetMovieListResponse> {
    override fun onLastPage(response: GetMovieListResponse): Boolean {
        return true
    }
}