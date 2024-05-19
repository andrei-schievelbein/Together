package br.com.noke.twogether.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MentorScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Logo()
    }
}