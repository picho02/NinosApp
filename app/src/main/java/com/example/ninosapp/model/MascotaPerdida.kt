package com.example.ninosapp.model

import java.io.Serializable

data class MascotaPerdida(var idDuenio:String, var idMascota: String, var nombre: String,var nacimiento: String,var sexo: Boolean,var raza: String,var esterilizado: Boolean
                          ,var talla: String, var foto: String, var detalles: String,var lugarExtravio: String,var fechaExtravio: String, var extraviado: Boolean):
    Serializable
