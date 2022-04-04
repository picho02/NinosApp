package com.example.ninosapp.model

import java.io.Serializable

data class Pet(var id: Int, var name: String, var age: Int,var gender: String,var brench: String,var esteril: String,var owner: String):
    Serializable
