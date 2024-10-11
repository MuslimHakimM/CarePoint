package com.study.selamatkan.data.source.remote.network

import com.study.selamatkan.data.source.remote.model.DataCovidResponse
import retrofit2.http.GET

interface ApiCovid {
    /**
     * API Data Covid
     */
    @GET("api/id/covid19/stats")
    suspend fun getDataCovid(): DataCovidResponse
}