package com.example.camerfoods

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

const val FICHIER = "mes_recettes"

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

fun Context.sauvegardeRecettes(recettes: List<Recette>){
    try {
        val jsonString = Gson().toJson(recettes)
        val fichier = File(this.filesDir,FICHIER)
        fichier.writeText(jsonString)
    } catch (e : Exception){
        e.printStackTrace()
    }
}

fun Context.sauvegarderListes(liste: List<Ingredient>){
    val nomFichier = "listes.json"
    val file = File(this.filesDir,nomFichier)
    file.writeText(Gson().toJson(liste))
}

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