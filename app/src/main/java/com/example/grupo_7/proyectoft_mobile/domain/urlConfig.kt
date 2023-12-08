package com.example.grupo_7.proyectoft_mobile.domain

data class Configuracion(val serverUrl: String)

class UrlConfig {
    fun getServerUrl(): String {
        //Es de nececidad cambiar el dominio de ngrok cada vez que se reinicia
        return "https://8189-2800-ac-11-2aa4-b1a7-115c-8d43-56c9.ngrok-free.app"
    }
}