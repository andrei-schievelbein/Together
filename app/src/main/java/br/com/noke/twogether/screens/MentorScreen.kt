package br.com.noke.twogether.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.noke.twogether.model.User
import br.com.noke.twogether.screens.common.Logo
import br.com.noke.twogether.screens.common.UserImage
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun MentorScreen(encodedUserJson: String, navController: NavHostController) {
    val decodedUserJson = URLDecoder.decode(encodedUserJson, StandardCharsets.UTF_8.toString())
    val user = Gson().fromJson(decodedUserJson, User::class.java)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Logo()
            UserProfileCard(user = user)
            UserProfileBox(user)
            Text(
                text = "Perfil: ${user.perfil}",
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            )
        }
        Button(
            onClick = { navController.navigate("listagem") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8)),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(55.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(
                topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
            )
        ) {
            Text(
                text = "VOLTAR",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun UserProfileCard(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEEEEE))
            .padding(16.dp)
    ) {
        if (user.imagemURL.isNotBlank()) {
            UserImage(
                imageUrl = user.imagemURL,
                height = 80.dp,
                width = 80.dp,
                border = 2.dp,
                borderColor = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${user.nome} ${user.sobrenome}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = user.cargo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
            Text(
                text = "Mentor",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun UserProfileBox(user: User) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(5.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Contato",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (expanded) {
                    Text(
                        text = """
                             E-mail: ${user.email}
                             Celular: ${user.celular}
                             Website: ${user.website}
                             Localização: ${user.endereco}
                        """.trimIndent(),
                        modifier = Modifier.padding(start = 5.dp, top = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileBox() {
    UserProfileBox(
        user = User(
            nome = "John",
            sobrenome = "Doe",
            cargo = "Desenvolvedor",
            endereco = "São Paulo, Brasil",
            imagemURL = "https://example.com/image.png"
        )
    )
}