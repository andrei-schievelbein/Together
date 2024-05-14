package br.com.noke.twogether.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel
import coil.compose.rememberAsyncImagePainter


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



// Composable function to display a single user item
@Composable
fun UserItem(user: User) {
    Log.d("UserItem", "Displaying user: ${user.nome} ${user.sobrenome}")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        UserImage(imageUrl = user.imagemURL) //composable
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "${user.nome} ${user.sobrenome}")
            Text(text = user.cargo)
        }
    }
}

// Composable function to display a list of users
@Composable
fun UserList(users: List<User>) {
    Log.d("UserList", "Displaying list of users")
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(users) { user ->
            UserItem(user) //composable
        }
    }
}

// Composable function to display the user screen
@Composable
fun UserScreen(viewModel: UserViewModel) {
    Log.d("UserScreen", "Displaying UserScreen")
    val users by viewModel.users.collectAsState()
    UserList(users = users) //composable
}