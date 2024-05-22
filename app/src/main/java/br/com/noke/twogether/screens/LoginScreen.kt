package br.com.noke.twogether.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.noke.twogether.R

@Composable
fun LoginScreen(navController: NavController) {
    // Estado para controlar a visibilidade da mensagem de erro
    var showError by remember { mutableStateOf(false) }

    // Função para alterar o estado de erro
    fun handleLoginClick() {
        showError = !showError
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(1.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Imagem do logo",
                        modifier = Modifier.size(150.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text(
                        text = "Entrar",
                        modifier = Modifier.padding(start = 8.dp, top = 1.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    )

                    Row {
                        Text(
                            text = "ou ",
                            modifier = Modifier.padding(start = 8.dp, top = 1.dp),
                            style = TextStyle(fontSize = 14.sp)
                        )
                        ClickableText(
                            text = AnnotatedString("cadastre-se"),
                            onClick = {},
                            style = TextStyle(fontSize = 14.sp, color = Color.Blue),
                            modifier = Modifier.padding(top = 1.dp)
                        )
                        Text(
                            text = " no Together",
                            style = TextStyle(fontSize = 14.sp)
                        )
                    }
                }

                Column (modifier = Modifier
                    .height(240.dp)
                ){
                    Spacer(modifier = Modifier.height(20.dp))

                    // Botões de login
                    LoginButton(
                        text = "Entrar com o Google",
                        imageRes = R.drawable.google,
                        onClick = { handleLoginClick() }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LoginButton(
                        text = "Entrar com Apple",
                        imageRes = R.drawable.apple,
                        onClick = { handleLoginClick() }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LoginButton(
                        text = "Entrar com Facebook",
                        imageRes = R.drawable.facebook,
                        onClick = { handleLoginClick() },
                        showError = showError
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(color = Color.Black)
                    )
                    Text(
                        text = "ou",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(2.dp)
                            .background(color = Color.Black)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(IntrinsicSize.Min)
                        .padding(1.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                value = "",
                                onValueChange = { },
                                label = { Text("Email ou telefone") },
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.height(0.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = "",
                                onValueChange = {},
                                label = { Text("Senha") },
                                visualTransformation = PasswordVisualTransformation(),
                                textStyle = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { isChecked ->
                            println("isChecked: $isChecked")
                        }
                    )
                    Text(
                        text = "Lembrar dos meus dados.",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Esqueceu a senha?",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color(0xFF03A9F4)
                        ),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                Button(
                    onClick = { navController.navigate("cadastro") },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 30.dp)
                        .height(55.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
                ) {
                    Text(
                        text = "Continuar",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LoginButton(
    text: String,
    imageRes: Int,
    onClick: () -> Unit,
    showError: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(IntrinsicSize.Min)
                .padding(3.dp)
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(7.dp))
                    .border(
                        width = 3.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(7.dp)
                    )
                    .clickable { onClick() }
            ) {
                Column(
                    modifier = Modifier
                        .height(40.dp)
                        .weight(0.1f)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(8.dp)
                ) {
                    Text(
                        text = text,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
        }

        if (showError) {
            Text(
                text = "Em desenvolvimento!",
                color = Color.Red,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .offset(y = (-10).dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    val fakeNavController = rememberNavController()
    LoginScreen(navController = fakeNavController)
}