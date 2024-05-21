package br.com.noke.twogether.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.noke.twogether.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    // Função para exibir o logotipo da aplicação
    Column {
        Spacer(modifier = Modifier.height(30.dp))
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
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun NotificationIcon(followerCount: Int, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(30.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Imagem do logo",
                modifier = Modifier.fillMaxHeight()
            )
        }
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.sino),
                contentDescription = "Ícone de sino",
                modifier = Modifier.size(30.dp)
            )
            if (followerCount > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Red, shape = CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = followerCount.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNotificationIcon() {
    NotificationIcon(followerCount = 5) {
        // Action when clicked (for preview, this can be empty)
    }
}