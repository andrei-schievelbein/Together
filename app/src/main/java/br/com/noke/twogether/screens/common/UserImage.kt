package br.com.noke.twogether.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun UserImage(
    imageUrl: String,
    height: Dp = 50.dp,
    width: Dp = 50.dp,
    border: Dp = 1.dp,
    borderColor: Color = Color.Black,
    modifier: Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .clip(CircleShape)
            .height(height)
            .width(width)
            .border(border, color = borderColor, shape = CircleShape)
    )
}