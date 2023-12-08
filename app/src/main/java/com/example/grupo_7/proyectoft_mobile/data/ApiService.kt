package com.example.grupo_7.proyectoft_mobile.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

     @POST("auth/login")
    // Anotación indicando que este método realiza una solicitud POST a la ruta
    fun loginUsuario(@Body user: loginSolicitud): Call<respuestaUsuario>
    // Método que permite iniciar sesión al usuario, toma un objeto 'user' del
    // tipo 'loginSolicitud' como cuerpo de la solicitud y devuelve un objeto Call
    // parametrizado con 'respuestaUsuario'.

    @GET("eventos")
    fun getAllEventos(@Header("Authorization") token: String?): Call<List<Evento>>

    @GET("eventos/{id}")
    fun getEventoId(@Path("id") id: Long?, @Header("Authorization") token: String?): Call<Evento>

    @GET("semestres")
    fun getAllSemestres(@Header("Authorization") token: String?): Call<List<Semestre>>

    @GET("semestres/{id}")
    fun getSemsetreId(@Path("id") id: Long?, @Header("Authorization") token: String?): Call<Semestre>

    @POST("reclamos")
    fun createReclamo(@Header("Authorization") token: String?, @Body reclamo: Reclamo?): Call<Reclamo>

    @GET("reclamos")
    fun getAllReclamos(@Header("Authorization") token: String?): Call<List<CompleteReclamo>>

    @PUT("reclamos")
    fun updateReclamo(@Header("Authorization") token: String?, @Body reclamo: Reclamo?): Call<Reclamo>

    @DELETE("reclamos/{id}")
    fun deleteReclamo(@Path("id") id: Long?,@Header("Authorization") token: String?): Call<Reclamo>
}