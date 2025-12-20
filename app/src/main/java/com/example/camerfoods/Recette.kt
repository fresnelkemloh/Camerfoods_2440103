package com.example.camerfoods
/**
 * Modèle de données représentant pour les recettes.
 *
 * Cette classe contient toutes les informations nécessaires pour afficher une recette
 * dans l'application,on a entre autre le texte, l'image associée a la recette et l'état des favoris.
 *
 * @property id Identifiant de la recette.
 * @property titre Le titre de la recette.
 * @property temps La durée de la préparation.
 * @property description La description de la recette.
 * @property ingredients La liste des ingrédients.
 * @property instructions Etapes de préparation.
 * @property image Le nom de la ressource de l'image de la recette.
 * @property favoris Indique si la recette est marquée comme favorite ou pas , mais par defaut c est false.
 */
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
