package br.com.noke.twogether.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import br.com.noke.twogether.R
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.model.User
import br.com.noke.twogether.notification.NotificationPush
import br.com.noke.twogether.screens.common.NotificationIcon
import br.com.noke.twogether.screens.common.UserImage
import br.com.noke.twogether.viewmodel.UserViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ListagemScreen(
    viewModel: UserViewModel,
    navController: NavHostController,
    notificationHelper: NotificationPush
) {
    // Obtenção das categorias de usuário e da lista de usuários do ViewModel
    val userCategories by viewModel.userCategories.collectAsState()
    val users by viewModel.users.collectAsState()

    // Estado que armazena todos os usuários
    var filteredUsers by remember { mutableStateOf(emptyList<User>()) }

    //Estado dos seguidores
    var followerCount by remember { mutableStateOf(0) }

    //Estados das mensagens
    var showNotificationPopup by remember { mutableStateOf(false) }
    var notificationMessages by remember { mutableStateOf(listOf<String>()) }

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

    Column(modifier = Modifier.padding(start = 8.dp)) {

        NotificationIcon(followerCount) {
            showNotificationPopup = true
        }

        // Componente de busca avançada
        AdvancedSearch(users, userCategories) { filtered ->
            filteredUsers = filtered
        }

        // Exibe a lista de usuários e chama UserItem para cada usuário
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
        ) {
            items(filteredUsers) { user ->
                UserItem(
                    user = user,
                    navController = navController,
                    notificationHelper = notificationHelper,
                    onFollow = {
                        followerCount++
                        notificationMessages =
                            notificationMessages + "Você está seguindo ${user.nome} ${user.sobrenome}"
                    },
                    onUnfollow = {
                        followerCount--
                        notificationMessages =
                            notificationMessages + "Você deixou de seguir ${user.nome} ${user.sobrenome}"
                    }
                )
            }
        }

        Button(
            onClick = { navController.navigate("categoria") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8)),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(
                topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
            )
        ) {
            Text(
                text = "Voltar aos #Tópicos Primários",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        if (showNotificationPopup) {
            NotificationPopup(
                followCount = followerCount,
                messages = notificationMessages,
                onDismiss = { showNotificationPopup = false },
                onClear = {
                    followerCount = 0
                    notificationMessages = listOf()
                    showNotificationPopup = false
                }
            )
        }
    }
}

@Composable
fun UserItem(
    user: User,
    navController: NavHostController,
    notificationHelper: NotificationPush,
    onFollow: () -> Unit,
    onUnfollow: () -> Unit
) {
    var isFollowing by remember { mutableStateOf(false) }
    var shouldNotify by remember { mutableStateOf<Pair<Boolean, Boolean>?>(null) }

    LaunchedEffect(shouldNotify) {
        shouldNotify?.let { (following, notify) ->
            if (notify) {
                val message = if (following) {
                    "Você está seguindo ${user.nome} ${user.sobrenome}"
                } else {
                    "Você deixou de seguir ${user.nome} ${user.sobrenome}"
                }
                notificationHelper.sendNotification("Notificação de Seguidor", message)
                shouldNotify = null // Resetar o estado para evitar múltiplas notificações

                if (following) {
                    onFollow()
                } else {
                    onUnfollow()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color.Black)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .clickable {
                    Log.d(
                        "UserItem",
                        "User clicked: ${user.nome} ${user.sobrenome}, ${user.cargo}, ${user.imagemURL}"
                    )
                    val userJson = Gson().toJson(user)
                    val encodedUserJson =
                        URLEncoder.encode(userJson, StandardCharsets.UTF_8.toString())
                    navController.navigate("mentor/$encodedUserJson")
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (user.imagemURL.isNotBlank()) {
                    UserImage(imageUrl = user.imagemURL)
                }
                Column(
                    modifier = Modifier
                        .weight(0.75f)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "${user.nome} ${user.sobrenome}",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.cargo,
                        style = TextStyle(
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .weight(0.2f)
                .height(60.dp)
                .clickable {
                    isFollowing = !isFollowing
                    shouldNotify = Pair(isFollowing, true)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isFollowing) {
                Image(
                    painter = painterResource(id = R.drawable.together),
                    contentDescription = "Small Image",
                    modifier = Modifier.size(35.dp)
                )
            }
            Text(
                text = if (isFollowing) "together" else "match",
                modifier = Modifier.padding(end = 10.dp),
                color = Color(0xFF34A5D8),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
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
    // Estado para controlar a visibilidade do Card
    var isCardVisible by remember { mutableStateOf(false) }

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
                val matchesCategory =
                    selectedCategories.isEmpty() || user.categories.map { it.name }
                        .intersect(selectedCategories).isNotEmpty()
                val matchesSearch = searchText.isEmpty() || user.nome.contains(
                    searchText,
                    ignoreCase = true
                ) || user.sobrenome.contains(searchText, ignoreCase = true)
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    filterUsers()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 6.dp),
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
            // Seta para abrir/fechar o Card
            IconButton(onClick = { isCardVisible = !isCardVisible }) {
                Icon(
                    imageVector = if (isCardVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isCardVisible) "Collapse" else "Expand"
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (searchText.isEmpty() && selectedCategories.isEmpty()) {
            Text(text = "#Tópicos primários:")
            // Exibe as categorias selecionadas
            if (userCategories.isNotEmpty()) {
                Text(
                    text = userCategories.joinToString(", ") { "#${it.replace("#", "").trim()}" },
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        } else if (selectedCategories.isNotEmpty()) {

            Text(text = "# Tópicos filtrados")
            // Exibe as categorias selecionadas no card
            if (selectedCategories.isNotEmpty()) {
                Text(
                    text = selectedCategories.joinToString(", ") {
                        "#${
                            it.replace("#", "").trim()
                        }"
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(text = "Resultado da busca", modifier = Modifier.padding(bottom = 10.dp))
        }

        // Card que aparece quando isCardVisible é verdadeiro
        if (isCardVisible) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp)
                    .border(3.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // FlowRow para exibir as categorias
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Category.values().forEach { category ->
                            val isSelected = selectedCategories.contains(category.name)
                            val backgroundColor =
                                if (isSelected) Color(0xFF03A9F4) else Color(0xFFE6E6E6)

                            Button(
                                modifier = Modifier
                                    .padding(2.dp)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                colors = ButtonDefaults.buttonColors(backgroundColor),
                                shape = RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 4.dp,
                                    bottomEnd = 4.dp,
                                    bottomStart = 4.dp
                                ),
                                contentPadding = PaddingValues(
                                    horizontal = 6.dp,  // Reduz o padding horizontal
                                    vertical = 4.dp     // Reduz o padding vertical
                                ),
                                onClick = {
                                    selectedCategories = if (isSelected) {
                                        selectedCategories - category.name
                                    } else {
                                        selectedCategories + category.name
                                    }
                                    filterUsers()
                                }
                            ) {
                                Text(
                                    modifier = Modifier.padding(2.dp),
                                    text = "#${category.name}",
                                    color = if (isSelected) Color.White else Color.DarkGray,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Composable
//fun NotificationIcon(followerCount: Int, onClick: () -> Unit) {
//    Box(
//        contentAlignment = Alignment.TopEnd,
//        modifier = Modifier
//            .size(48.dp)
//            .clickable { onClick() }
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.sino),
//            contentDescription = "Ícone de sino",
//            modifier = Modifier.size(40.dp)
//        )
//        if (followerCount > 0) {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .size(24.dp)
//                    .background(Color.Red, shape = CircleShape)
//                    .align(Alignment.TopEnd)
//            ) {
//                Text(
//                    text = followerCount.toString(),
//                    color = Color.White,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//        }
//    }
//}

@Composable
fun NotificationPopup(
    followCount: Int,
    messages: List<String>,
    onDismiss: () -> Unit,
    onClear: () -> Unit
) {
    Popup(alignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(16.dp)
                    .border(2.dp, color = Color.Black, RoundedCornerShape(12.dp))
                    .clickable { },
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Você tem $followCount novos seguidores.",
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    LazyColumn {
                        items(messages) { message ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = message)
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = Color.Black)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            onClick = onClear,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8)),
                            shape = RoundedCornerShape(
                                topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
                            )
                        ) {
                            Text("Limpar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8)),
                            shape = RoundedCornerShape(
                                topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
                            ),
                        )
                        {
                            Text("Fechar")
                        }
                    }
                }
            }
        }
    }
}
