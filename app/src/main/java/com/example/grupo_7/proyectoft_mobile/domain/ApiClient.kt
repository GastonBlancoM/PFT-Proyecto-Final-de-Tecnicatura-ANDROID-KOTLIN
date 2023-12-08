package com.example.grupo_7.proyectoft_mobile.domain

import com.example.grupo_7.proyectoft_mobile.data.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val config = UrlConfig()
    private val BASE_URL = "${config.getServerUrl()}/ProyectoFT/rest/"


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}