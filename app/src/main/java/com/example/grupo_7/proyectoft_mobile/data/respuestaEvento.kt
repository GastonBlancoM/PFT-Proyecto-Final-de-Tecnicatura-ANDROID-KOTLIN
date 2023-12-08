package com.example.grupo_7.proyectoft_mobile.data

data class Evento (
    val idEvento: Long,
    val titulo: String,
    val informacion: String,
    val tipoEvento: String,
    val fechaInicio: Long,
    val fechaFin: Long,
    val modalidad: String,
    val localizacion: String,
    val creditos: Int,
    val estadoEvento: String,
    val itr: Itr,
    val listaTutores: List<Tutor>
)

data class Tutor (
    val idUsuario: Long,
    val nombre: String
)