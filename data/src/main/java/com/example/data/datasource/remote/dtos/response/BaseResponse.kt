package com.example.data.datasource.remote.dtos.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    val dates:Dates?,
    val page:Int?,
    val result:T?,
    @SerializedName("total_pages")
    val totalPage:Int?,
    @SerializedName("total_results")
    val totalResults:Int?
)

data class Dates(
    val maximum:String?,
    val minimum:String?
)
