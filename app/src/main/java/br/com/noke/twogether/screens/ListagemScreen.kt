package br.com.noke.twogether.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.noke.twogether.R
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun Logo(modifier: Modifier = Modifier) {
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
    val userCategories by viewModel.userCategories.collectAsState()
    val users by viewModel.users.collectAsState()
    val filteredUsers = users.filter { user ->
        user.imagemURL.isNotBlank() && user.categories.map { it.displayName }
            .intersect(userCategories.toSet()).isNotEmpty()
    }

    LaunchedEffect(Unit) {
        viewModel.fetchUserCategories()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Logo()
        if (userCategories.isNotEmpty()) {
            Text(
                text = userCategories.joinToString(", "),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        LazyColumn {
            items(filteredUsers) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        if (user.imagemURL.isNotBlank()) {
            UserImage(imageUrl = user.imagemURL)
        } else {
            Spacer(modifier = Modifier.height(48.dp)) //
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











