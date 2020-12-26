package me.saurabhrane.mvp.data.remote

import me.saurabhrane.mvp.data.repository.NetworkResponseModel

class GetCastAndCrewResponse(
    val id: Int? = null,
    val cast: ArrayList<Cast>? = null,
    val crew: ArrayList<Crew>? = null
) : NetworkResponseModel