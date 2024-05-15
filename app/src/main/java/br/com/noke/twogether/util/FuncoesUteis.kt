package br.com.noke.twogether.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel

@Composable //Importar dados do JSON para o Firestore
fun ExportToFirestore(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        importDataFromJson(context)
    }
}

@Composable //Deletar todos os documentos da coleção "users"
fun DeleteAllDocuments(modifier: Modifier = Modifier) {
    deleteAllDocumentsFromCollection("users")
}

@Composable //lista dos os usuários do Firestore por nome e sobrenome
fun ReadAllDocuments(viewModel: UserViewModel, modifier: Modifier = Modifier) {
    //Ler Nome e Sobrenome de todos os usuários do Firestore
    val users by viewModel.users.collectAsState()
    LazyColumn(modifier = Modifier.height(200.dp)) {
        items(users) { user ->
            Text(text = "User: ${user.nome} ${user.sobrenome}")
        }}}

@Composable
fun AddUserScreen(viewModel: UserViewModel) {
    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("First Name") }
        )
        TextField(
            value = sobrenome,
            onValueChange = { sobrenome = it },
            label = { Text("Last Name") }
        )
        Button(onClick = {
            viewModel.addUser(User(nome = nome, sobrenome = sobrenome)) { success ->
                showMessage = success
            }
        }) {
            Text("Add User")
        }
        if (showMessage) {
            Text("User added successfully!")
        }
    }
}






