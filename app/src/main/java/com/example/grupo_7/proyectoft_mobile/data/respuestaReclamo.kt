package com.example.grupo_7.proyectoft_mobile.data

data class Reclamo(
    val idReclamo: Long?,
    val titulo: String?,
    val tipoReclamo: String?,
    val detalle: String?,
    val idSemestre: Long?,
    val estadoReclamo: String?,
    val idEstudiante: Long?,
    val idEvento: Long?
)

data class CompleteReclamo(
    val idReclamo: Long?,
    val titulo: String?,
    val tipoReclamo: String?,
    val detalle: String?,
    val semestre: Semestre,
    val estado: String?,
    val estudiante: Estudiante?,
    val evento: Evento
)

