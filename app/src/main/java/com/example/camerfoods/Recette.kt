package com.example.camerfoods

data class Recette(
    val id: Int,
    val titre: String,
    val temps: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val image: String,
    var favoris: Boolean= false
)
