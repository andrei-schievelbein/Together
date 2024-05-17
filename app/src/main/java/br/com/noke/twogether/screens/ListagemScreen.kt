package br.com.noke.twogether.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.noke.twogether.R
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun Logo(modifier: Modifier = Modifier) {
    // Função para exibir o logotipo da aplicação
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagem do logo",
            )
        }
    }
}

@Composable
fun ListagemScreen(viewModel: UserViewModel, navController: NavHostController) {
    // Obtenção das categorias de usuário e da lista de usuários do ViewModel
    val userCategories by viewModel.userCategories.collectAsState()
    val users by viewModel.users.collectAsState()

    // Estado que armazena todos os usuários
    var filteredUsers by remember { mutableStateOf(emptyList<User>()) }

    // Efeito que busca as categorias de usuário quando a tela é carregada
    LaunchedEffect(Unit) {
        viewModel.fetchUserCategories()
    }

    // Efeito que inicializa a filtragem de usuários quando `users` ou `userCategories` mudam
    LaunchedEffect(users, userCategories) {
        filteredUsers = users.filter { user ->
            user.imagemURL.isNotBlank() && user.categories.map { it.displayName }
                .intersect(userCategories.toSet()).isNotEmpty()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Logo()

        // Componente de busca avançada
        AdvancedSearch(users, userCategories) { filtered ->
            filteredUsers = filtered
        }

        // Exibe as categorias selecionadas
        if (userCategories.isNotEmpty()) {
            Text(
                text = userCategories.joinToString(", ") { "#${it.replace("#", "").trim()}" },
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )
        }

        // Exibe a lista de usuários
        LazyColumn {
            items(filteredUsers) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    // Componente para exibir um item de usuário
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(2.dp)
            .background(color = Color.Black)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (user.imagemURL.isNotBlank()) {
            UserImage(imageUrl = user.imagemURL)
        } else {
            Spacer(modifier = Modifier.height(48.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "${user.nome} ${user.sobrenome}")
            Text(text = user.cargo)
        }
    }
}

@Composable
fun UserImage(imageUrl: String) {
    // Componente para exibir a imagem do usuário
    Log.d("UserImage", "Loading image: $imageUrl")
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .clip(CircleShape)
            .height(50.dp)
            .width(50.dp)
            .border(1.dp, color = Color.Black, shape = CircleShape)
    )
}

@Composable
fun AdvancedSearch(
    users: List<User>,
    userCategories: List<String>,
    onFilteredUsers: (List<User>) -> Unit
) {
    // Estado para armazenar o texto de busca
    var searchText by remember { mutableStateOf("") }
    // Estado para armazenar as categorias selecionadas
    var selectedCategories by remember { mutableStateOf(setOf<String>()) }
    // Estado para armazenar os usuários filtrados
    var filteredUsers by remember { mutableStateOf(emptyList<User>()) }

    // Função para realizar a filtragem dos usuários
    fun filterUsers() {
        filteredUsers = if (searchText.isEmpty() && selectedCategories.isEmpty()) {
            // Filtra por categoria e imageURL quando não há busca ativa
            users.filter { user ->
                user.imagemURL.isNotBlank() && user.categories.map { it.displayName }
                    .intersect(userCategories.toSet()).isNotEmpty()
            }
        } else {
            // Filtra por busca avançada
            users.filter { user ->
                val matchesCategory = selectedCategories.isEmpty() || user.categories.map { it.name }.intersect(selectedCategories).isNotEmpty()
                val matchesSearch = searchText.isEmpty() || user.nome.contains(searchText, ignoreCase = true) || user.sobrenome.contains(searchText, ignoreCase = true)
                val hasImage = user.imagemURL.isNotBlank()
                matchesCategory && matchesSearch && hasImage
            }
        }
        onFilteredUsers(filteredUsers)
    }

    // Efeito que chama a função de filtragem quando `users`, `selectedCategories` ou `searchText` mudam
    LaunchedEffect(users, selectedCategories, searchText) {
        filterUsers()
    }

    Column {
        // LazyRow para exibir as categorias
        LazyRow(
            modifier = Modifier.padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Category.values().map { it.name }) { category ->
                val isSelected = selectedCategories.contains(category)
                val backgroundColor = if (isSelected) Color.Blue else Color.Gray
                Text(
                    text = "#${category}",
                    modifier = Modifier
                        .background(backgroundColor)
                        .padding(8.dp)
                        .clickable {
                            selectedCategories = if (isSelected) {
                                selectedCategories - category
                            } else {
                                selectedCategories + category
                            }
                            filterUsers()
                        },
                    color = Color.White
                )
            }
        }

        // Campo de busca
        OutlinedTextField(
            value = searchText,
            onValueChange = { newValue ->
                searchText = newValue
                filterUsers()
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search...") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.clickable {
                        filterUsers()
                    }
                )
            },
            singleLine = true
        )
    }
}
