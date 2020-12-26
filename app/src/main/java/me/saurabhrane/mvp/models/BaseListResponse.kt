package me.saurabhrane.mvp.models

import com.google.gson.annotations.SerializedName
import me.saurabhrane.mvp.data.repository.NetworkResponseModel

open class BaseListResponse<Item>(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("results") var results: ArrayList<Item>? = null
) : NetworkResponseModel