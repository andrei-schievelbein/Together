package br.com.noke.twogether.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

fun deleteAllDocumentsFromCollection(collectionPath: String) {
    val db = FirebaseFirestore.getInstance()
    // Referência para a coleção
    db.collection(collectionPath)
        .get()
        .addOnSuccessListener { result ->
            // Loop para deletar cada documento encontrado
            for (document in result) {
                db.collection(collectionPath).document(document.id).delete()
                    .addOnSuccessListener {
                        Log.d("Firestore", "Document successfully deleted!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error deleting document", e)
                    }
            }
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error getting documents: ", e)
        }
}
