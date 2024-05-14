package br.com.noke.twogether.repository

import android.util.Log
import br.com.noke.twogether.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UserRepository(private val db: FirebaseFirestore) {
    suspend fun addUser(user: User): Boolean = withContext(Dispatchers.IO) {
        try {
            // Transforma as categorias enum em strings antes de salvar
            val userMap = user.toMap()
            db.collection("users").add(userMap).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    //Cadastra User e retorna o ID do usuário criado
    suspend fun addUserAndGetId(user: User): String? = withContext(Dispatchers.IO) {
        try {
            val result = db.collection("users").add(user.toMap()).await()
            result.id  // Retorna o ID do usuário criado
        } catch (e: Exception) {
            null  // Retorna null se houver erro
        }
    }

    // Atualiza as categorias do usuário
    suspend fun updateUserCategories(userId: String, categories: List<String>): Boolean = withContext(Dispatchers.IO) {
        try {
            db.collection("users").document(userId)
                .update("categories", categories)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Função para converter User para um Map antes de salvar no Firestore
    private fun User.toMap(): Map<String, Any> {
        return mapOf(
            "nome" to nome,
            "sobrenome" to sobrenome,
            "categories" to categories.map { it.name },  // Converte enums para strings
            "cargo" to cargo,
            "perfil" to perfil,
            "habilidades" to habilidades,
            "celular" to celular,
            "email" to email,
            "endereço" to endereco,
            "website" to website,
            "imagemURL" to imagemURL,
            "seguir" to seguir,
            "match" to match,
            "aprendizado" to aprendiz
        )
    }



//    fun getUsers(): Flow<List<User>> = callbackFlow {
//        val listenerRegistration = db.collection("users")
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    Log.e("UserRepository", "Error fetching users", e)
//                    close(e) // Fechar o fluxo com erro
//                    return@addSnapshotListener
//                }
//
//                val users = snapshot?.documents?.mapNotNull {
//                    it.toObject<User>(User::class.java)
//                    Log.d("UserRepository", "Fetched user: $user")
//                    user
//
//                }
//                trySend(users ?: emptyList()).isSuccess
//            }
//
//        awaitClose {
//            listenerRegistration.remove()
//        }
//    }
suspend fun getUsers(): Flow<List<User>> = callbackFlow {
    val listenerRegistration = db.collection("users")
        .addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("UserRepository", "Error fetching users", e)
                close(e) // Fechar o fluxo com erro
                return@addSnapshotListener
            }

            val users = snapshot?.documents?.mapNotNull { document ->
                val user = document.toObject(User::class.java)
                Log.d("UserRepository", "Fetched user: $user")
                user
            }
            trySend(users ?: emptyList()).isSuccess
        }

    awaitClose {
        listenerRegistration.remove()
    }
}
}


