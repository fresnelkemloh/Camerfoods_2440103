package com.example.camerfoods

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
/**
 * Fichier dans lequel se fait la sauvegarde interne.
 */
const val FICHIER = "mes_recettes"

/**
 * Charge la liste des recettes de l'application en verifiant si le fichier existe et a la charger si il existe.
 *
 * @return Une liste d'objets [Recette]. Retourne une liste vide en cas d'erreur de lecture.
 */
fun Context.lireRecettes(): List<Recette>{
    val fichier= File(this.filesDir,FICHIER)
    if (fichier.exists()){
        try {
            val jsonString = fichier.readText()
            val typeList = object: TypeToken<List<Recette>>(){}.type
            return Gson().fromJson(jsonString,typeList)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    try {
        val jsonString= assets.open("recettes.json").bufferedReader().use { it.readText() }
        val typeListe = object : TypeToken<List<Recette>>(){}.type
        val recetteInitiale: List<Recette> = Gson().fromJson(jsonString,typeListe)
        return recetteInitiale
    } catch (e: Exception){
        e.printStackTrace()
        return emptyList()
    }
}

/**
 * Sauvegarde la liste complète des recettes dans le stockage interne.
 *
 * @param recettes La liste des recettes à sauvegarder.
 */
fun Context.sauvegardeRecettes(recettes: List<Recette>){
    try {
        val jsonString = Gson().toJson(recettes)
        val fichier = File(this.filesDir,FICHIER)
        fichier.writeText(jsonString)
    } catch (e : Exception){
        e.printStackTrace()
    }
}

/**
 * Sauvegarde la liste de courses.
 *
 * Les données sont écrites dans listes.json dans le stockage interne de l'application.
 *
 * @param liste La liste des objets [Ingredient] à sauvegarder.
 */
fun Context.sauvegarderListes(liste: List<Ingredient>){
    val nomFichier = "listes.json"
    val file = File(this.filesDir,nomFichier)
    file.writeText(Gson().toJson(liste))
}

/**
 * Permet de lire la liste de courses.
 *
 * @return Une liste d'objets [Ingredient]. Retourne une liste vide si le fichier n'existe pas encore.
 */
fun Context.lireListes(): List<Ingredient>{
    val nomFichier = "listes.json"
    val file = File(this.filesDir,nomFichier)
    if (file.exists()){
        val jsonString = file.readText()
        val type = object : TypeToken<List<Ingredient>>(){}.type
        return Gson().fromJson(jsonString,type)
    } else{
        return emptyList()
    }
}