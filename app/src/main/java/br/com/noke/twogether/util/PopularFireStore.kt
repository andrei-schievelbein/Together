package br.com.noke.twogether.util

import android.content.Context
import br.com.noke.twogether.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

//função para importar os dados do json para o firebase
fun importDataFromJson(context: Context) {
    val jsonString: String
    try {
        jsonString = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val listOfUsersType = object : TypeToken<List<User>>() {}.type
        val users: List<User> = gson.fromJson(jsonString, listOfUsersType)
        val db = Firebase.firestore
        users.forEach { user ->
            db.collection("users").add(user)
                .addOnSuccessListener { documentReference ->
                    println("DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Error adding document $e")
                }
        }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    }
}

