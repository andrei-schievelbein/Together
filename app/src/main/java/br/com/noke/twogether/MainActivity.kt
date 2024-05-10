package br.com.noke.twogether


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import br.com.noke.twogether.factory.ViewModelFactory
import br.com.noke.twogether.repository.UserRepository
import br.com.noke.twogether.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.model.User


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Criação do UserRepository
        val userRepository = UserRepository(FirebaseFirestore.getInstance())
        // Criação da Factory
        val factory = ViewModelFactory(userRepository)

        // Obtenção do ViewModel
        val viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        setContent {
            UserScreen(viewModel)
        }
    }
}

@Composable
fun UserScreen(viewModel: UserViewModel, modifier: Modifier = Modifier.fillMaxSize()) {
    val users by viewModel.users.collectAsState()
    Column {
        AddUserScreen(viewModel)
        LazyColumn(modifier = Modifier.height(200.dp)) {
            items(users) { user ->
                Text(text = "User: ${user.first} ${user.last}")
            }
        }
        CategoriesSelectionScreen(viewModel)
    }
}

@Composable
fun AddUserScreen(viewModel: UserViewModel) {
    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }

    Column {
        TextField(
            value = first,
            onValueChange = { first = it },
            label = { Text("First Name") }
        )
        TextField(
            value = last,
            onValueChange = { last = it },
            label = { Text("Last Name") }
        )
        Button(onClick = {
            viewModel.addUser(User(first = first, last = last)) { success ->
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


//@Composable
//fun AddUserScreen(viewModel: UserViewModel) {
//    var first by remember { mutableStateOf("") }
//    var last by remember { mutableStateOf("") }
//    var showMessage by remember { mutableStateOf(false) }
//
//    Column {
//        TextField(
//            value = first,
//            onValueChange = { first = it },
//            label = { Text("First Name") }
//        )
//        TextField(
//            value = last,
//            onValueChange = { last = it },
//            label = { Text("Last Name") }
//        )
//        Button(onClick = {
//            viewModel.addUser(User(first = first, last = last)) { success ->
//                showMessage = success
//            }
//        }) {
//            Text("Add User")
//        }
//        if (showMessage) {
//            Text("User added successfully!")
//        }
//    }
//}

@Composable
fun CategoriesSelectionScreen(viewModel: UserViewModel) {
    val categories = Category.values()
    val selectedCategories = remember { mutableStateListOf<Category>() }

    LazyColumn {
        items(categories) { category ->
            var isChecked by remember { mutableStateOf(false) }
            Row(Modifier.toggleable(value = isChecked, onValueChange = {
                isChecked = it
                if (isChecked) {
                    selectedCategories.add(category)
                } else {
                    selectedCategories.remove(category)
                }
            })) {
                Checkbox(checked = isChecked, onCheckedChange = null)
                Text(text = category.name.replace('_', ' '))
            }
        }
        item {
            Button(onClick = {
                if (selectedCategories.size in 3..28) {
                    viewModel.updateUserCategories(selectedCategories.toList()) { success ->
                        // Trate o resultado aqui, como mostrar uma mensagem de sucesso ou erro
                    }
                }
            }) {
                Text("Salvar Categorias")
            }
        }
    }
}


//@Composable
//fun CategoriesSelectionScreen(viewModel: UserViewModel) {
//    val categories = Category.values()  // Retorna todas as instâncias do enum Category
//    val selectedCategories = remember { mutableStateListOf<Category>() }
//
//    // Usar LazyColumn para tornar a lista rolável
//    LazyColumn {
//        items(categories) { category ->
//            var isChecked by remember { mutableStateOf(false) }
//
//            Row(Modifier.toggleable(
//                value = isChecked,
//                onValueChange = {
//                    isChecked = it
//                    if (isChecked) {
//                        selectedCategories.add(category)
//                    } else {
//                        selectedCategories.remove(category)
//                    }
//                }
//            )) {
//                Checkbox(
//                    checked = isChecked,
//                    onCheckedChange = null  // Controle de estado é feito pelo toggleable
//                )
//                Text(text = category.name.replace('_', ' '))  // Mostra o nome da categoria com espaços
//            }
//        }
//        item {
//            // Botão para salvar as categorias
//            Button(onClick = {
//                if (selectedCategories.size in 3..28) {  // Assegura que entre 3 e 28 categorias sejam selecionadas
//                    viewModel.addUser(User(first = "First", last = "Last", categories = selectedCategories.toList()))
//                } else {
//                    // Adicione lógica para tratar a seleção inválida de categorias
//                }
//            }) {
//                Text("Salvar Categorias")
//            }
//        }
//    }
//}







