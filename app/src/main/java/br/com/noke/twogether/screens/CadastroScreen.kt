package br.com.noke.twogether.screens

import br.com.noke.twogether.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel


@Composable
fun CadastroScreen(viewModel: UserViewModel, navController: NavController) {
    var nome by remember { mutableStateOf("teste") }
    var sobrenome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("teste") }
    var celular by remember { mutableStateOf("teste") }
    var senha by remember { mutableStateOf("teste") }
    var confirmacao by remember { mutableStateOf("teste") }
    var showError by remember { mutableStateOf(false) }




    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                //.background(color = Color.Cyan)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Imagem do logo",
                    modifier = Modifier
                        .size(100.dp)
                    //.padding(2.dp)
                )
            }

            // AQUI COLOQUEI O TEXTO CADASTRE-SE //

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                //.background(color = Color.Cyan)
            ) {
                Column {
                    Text(
                        text = "Cadastre-se",
                        modifier = Modifier.padding(start = 8.dp, top = 1.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    )
                }
            }

            // Campos de texto do formulário
            Column(modifier = Modifier.fillMaxWidth()) {

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { TextWithIcon("Seu nome", Icons.Outlined.AccountCircle) },
                    modifier = Modifier
                        .padding(top = 10.dp)
//                        .width(380.dp)
//                        .height(70.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = email, // valor inicial do campo
                    onValueChange = { email = it }, // função para atualizar o valor
                    label = { TextWithIcon("Seu e-mail", Icons.Filled.Email) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(380.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = celular, // valor inicial do campo
                    onValueChange = { celular = it }, // função para atualizar o valor
                    label = { TextWithIcon("Seu número de celular", Icons.Filled.Call) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(380.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = senha, // valor inicial do campo
                    onValueChange = { senha = it }, // função para atualizar o valor
                    label = { TextWithIcon("Senha numérica de 8 dígitos!", Icons.Outlined.Lock) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(380.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = confirmacao,
                    onValueChange = { confirmacao = it },
                    label = { TextWithIcon("Confirme a senha", Icons.Outlined.Lock) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(380.dp)
                        .height(70.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            if (showError) {
                Text(
                    text = "Favor preencha todos os campos.",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = {
                    val nameIsValid = nome.isNotBlank()
                    val emailIsValid = email.isNotBlank() && "@" in email
                    val phoneIsValid = celular.isNotBlank() && celular.all { it.isDigit() }

                    if (nameIsValid && emailIsValid && phoneIsValid) {
                        viewModel.addUser(
                            User(
                                nome = nome,
                                email = email,
                                celular = celular
                            )
                        )
                        navController.navigate("categoria")
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .width(350.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                    bottomEnd = 4.dp,
                    bottomStart = 4.dp
                ),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8))
            )


            {
                Text(
                    text = "Criar conta",
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


@Composable
fun TextWithIcon(text: String, icon: ImageVector, contentDescription: String? = null) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CadastroScreenPreview() {
    val fakeNavController = rememberNavController()
    CadastroScreen(viewModel = viewModel(), navController = fakeNavController)
}