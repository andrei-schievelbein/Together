package br.com.noke.twogether.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
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
                    modifier = Modifier
                        .size(100.dp)
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
                    // Texto "ou"
                    Text(
                        text = "ou ",
                        modifier = Modifier.padding(start = 8.dp, top = 1.dp),
                        style = TextStyle(fontSize = 14.sp)
                    )
                    ClickableText(
                        text = AnnotatedString("cadastre-se"),
                        onClick = { offset ->
                            // Adicione a ação de clique desejada aqui
                        },
                        style = TextStyle(fontSize = 14.sp, color = Color.Blue),
                        modifier = Modifier.padding(top = 1.dp)
                    )
                    Text(
                        text = " no Together",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(IntrinsicSize.Min)
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(4.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(7.dp)
                        )
                )
                {
                    Column(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(8.dp)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = "Logo do Google",
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
                            text = "Entrar com o Google",
                            style = TextStyle(
                                //fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(IntrinsicSize.Min)
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(size = 0.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(7.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.apple),
                            contentDescription = "Logo do Apple",
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
                            text = "Entrar com Apple",
                            style = TextStyle(
                                //fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(IntrinsicSize.Min)
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
                // .background(color = Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(size = 0.dp))
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(7.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.facebook),
                            contentDescription = "Logo do Facebook",
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
                            text = "Entrar com Facebook",
                            style = TextStyle(
                                //fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {
                Column {
                    Text(
                        text = "ou entre com os seus dados e senha.",
                        modifier = Modifier.padding(start = 80.dp, top = 10.dp),
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        OutlinedTextField(
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

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth(),
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
                //.padding(top = 1.dp),
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
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(75.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                //.padding(top = 1.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                Text(
                    text = "Lembrar dos meus dados.",
                    style = TextStyle(
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Button(
                onClick = { navController.navigate("cadastro") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                ),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
            )

            {
                Text(
                    text = "Continuar",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    val fakeNavController = rememberNavController()
    LoginScreen(navController = fakeNavController)
}