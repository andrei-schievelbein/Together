package br.com.noke.twogether.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.viewmodel.UserViewModel



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriaScreen(viewModel: UserViewModel, navController: NavHostController) {
    val categories = Category.values()
    val selectedCategories = remember { mutableStateOf(setOf<Category>()) }
    var buttonText by remember { mutableStateOf("Continuar") }
    var buttonColor by remember { mutableStateOf(Color(0xFF34A5D8)) }

        if (selectedCategories.value.size in 1..2) {
            buttonText = "Continuar"
            buttonColor = Color(0xFF34A5D8) // Cor original do botão
        } else {
            buttonText = "Selecione de 1 a 2 categorias"
            buttonColor = Color.LightGray // Cor mais clara
        }

    FlowRow(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
            CategoryButton(
                category = category.displayName,
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

                if (selectedCategories.value.size in 1..2) {
                    viewModel.updateUserCategories(selectedCategories.value.toList()) { success ->
                        navController.navigate("listagem")
                    // Aqui você pode tratar o resultado do sucesso ou falha
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier
                .padding(top = 20.dp)
                .width(350.dp)
                .height(60.dp),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomEnd = 4.dp,
                bottomStart = 4.dp
            )
        ) {
            Text(buttonText)
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
