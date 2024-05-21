package br.com.noke.twogether.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.noke.twogether.model.Category
import br.com.noke.twogether.screens.common.Logo
import br.com.noke.twogether.viewmodel.UserViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriaScreen(viewModel: UserViewModel, navController: NavHostController) {
    val categories = Category.values()
    val selectedCategories = remember { mutableStateOf(setOf<Category>()) }
    var buttonText by remember { mutableStateOf("Continuar") }
    var buttonColor by remember { mutableStateOf(Color(0xFF34A5D8)) }
    var buttonBorderWidth by remember { mutableStateOf(2.dp) }
    var buttonBorderColor by remember { mutableStateOf(Color.Black) }

    if (selectedCategories.value.size in 1..3) {
        buttonText = "Continuar"
        buttonColor = Color(0xFF34A5D8)
        buttonBorderColor = Color(0xFF34A5D8)
    } else {
        buttonText = "selecione 1 a 3 #topics"
        buttonColor = Color.LightGray
        buttonBorderWidth = 2.dp
        buttonBorderColor = Color.Black
    }
    Column(
        modifier = Modifier.padding(start = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Row(
            modifier = Modifier
                .padding(start = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "QUAIS SEUS INTERESSES?", style = TextStyle(
                    fontSize = 28.sp, fontWeight = FontWeight.Bold
                )
            )
        }
        Row(
            modifier = Modifier
                .padding(start = 4.dp, top = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Siga os #Tópicos para encontrar seus mentores.", maxLines = 2
            )
        }

        FlowRow(
            modifier = Modifier.padding(start = 2.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { category ->
                CategoryButton(category = category.displayName,
                    isSelected = selectedCategories.value.contains(category),
                    onSelectedChange = { isSelected ->
                        val newSet = selectedCategories.value.toMutableSet()
                        if (isSelected) newSet.add(category) else newSet.remove(category)
                        selectedCategories.value = newSet
                    })
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {

                        if (selectedCategories.value.size in 1..3) {
                            viewModel.updateUserCategories(selectedCategories.value.toList()) { success ->
                                navController.navigate("listagem")
                                // Aqui você pode tratar o resultado do sucesso ou falha
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    modifier = Modifier
                        .padding(top = 15.dp, end = 10.dp)
                        .width(350.dp)
                        .border(buttonBorderWidth, buttonBorderColor, RoundedCornerShape(4.dp))
                        .height(55.dp),
                    shape = RoundedCornerShape(
                        topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
                    )
                ) {
                    Text(buttonText, style = TextStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.Bold
                    ))
                }
            }
        }
    }
}


@Composable
fun CategoryButton(category: String, isSelected: Boolean, onSelectedChange: (Boolean) -> Unit) {
    Button(
        onClick = { onSelectedChange(!isSelected) },
        modifier = Modifier
            .height(45.dp)
            .padding(top = 5.dp, bottom = 5.dp),
        shape = RoundedCornerShape(
            topStart = 4.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 4.dp
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


