package com.example.camerfoods
/**
 * Modèle de données d'un article.
 *
 * Cette classe est utilisée pour gérer les éléments ajoutés par l'utilisateur.
 *
 * @property nom Le nom de l'article.
 * @property acheter État de l'article.
 */
data class Ingredient (
    val nom: String,
    var acheter: Boolean = false
)
