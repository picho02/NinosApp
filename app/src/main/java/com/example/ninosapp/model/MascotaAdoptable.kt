package com.example.ninosapp.model

import java.io.Serializable

data class MascotaAdoptable(var idDuenio:String, var idMascota: String, var nombre: String,var nacimiento: String,var sexo: Boolean,var raza: String,var esterilizado: Boolean
,var talla: String, var foto: String, var detalles: String, var sociable: String, var necesitaEjercicio: String, var energia: String):
    Serializable
