package br.com.noke.twogether


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import br.com.noke.twogether.factory.ViewModelFactory
import br.com.noke.twogether.repository.UserRepository
import br.com.noke.twogether.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.model.User
import br.com.noke.twogether.util.addDataToFirestore
import br.com.noke.twogether.util.deleteAllDocumentsFromCollection
import br.com.noke.twogether.util.importDataFromJson
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import br.com.noke.twogether.R
import br.com.noke.twogether.screens.UserScreen
import coil.compose.AsyncImage
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this)

        // Criação do UserRepository
        val userRepository = UserRepository(FirebaseFirestore.getInstance())
        // Criação da Factory
        val factory = ViewModelFactory(userRepository)

        // Obtenção do ViewModel
        val viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        setContent {
            UserScreen(viewModel)
//            HelloWorld()
        }
    }
}

@Composable
fun HelloWorld() {
    Text(text = "Hello World")
}

@Composable
fun UserScreen2(viewModel: UserViewModel, modifier: Modifier = Modifier.fillMaxSize()) {
//    val users by viewModel.users.collectAsState()
//    val context = LocalContext.current
//    LaunchedEffect(key1 = Unit) {
//        importDataFromJson(context)
//    }
//
//        addDataToFirestore(viewModel)
//        deleteAllDocumentsFromCollection("users")
//        CategoriesScreen(viewModel)
//        LazyColumn(modifier = Modifier.height(200.dp)) {
//            items(users) { user ->
//                Text(text = "User: ${user.nome} ${user.sobrenome}")
//            }
//        }
//        CategoriesSelectionScreen(viewModel)


    }


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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesScreen(viewModel: UserViewModel) {
    val categories = Category.values()
    val selectedCategories = remember { mutableStateOf(setOf<Category>()) }

    FlowRow(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryButton(
                category = category.displayName,  // Use displayName para o botão
                isSelected = selectedCategories.value.contains(category),
                onSelectedChange = { isSelected ->
                    val newSet = selectedCategories.value.toMutableSet()
                    if (isSelected) newSet.add(category) else newSet.remove(category)
                    selectedCategories.value = newSet
                }
            )
        }
        Button(
            onClick = {
                if (selectedCategories.value.size in 3..28) {
                    viewModel.updateUserCategories(selectedCategories.value.toList()) { success ->
                        // Aqui você pode tratar o resultado do sucesso ou falha
                    }
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Continuar")
        }
    }
}


@Composable
fun CategoryButton(category: String, isSelected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    Button(
        onClick = { onSelectedChange(!isSelected) },
        modifier = Modifier.padding(0.dp),
        shape = RoundedCornerShape(
            topStart = 4.dp,
            topEnd = 4.dp,
            bottomEnd = 4.dp,
            bottomStart = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF03A9F4) else Color(0xFFE6E6E6)
        ),
        contentPadding = PaddingValues(
            horizontal = 6.dp,  // Reduz o padding horizontal
            vertical = 4.dp     // Reduz o padding vertical
        )
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.White else Color.DarkGray,
            fontSize = 16.sp
        )
    }
}

