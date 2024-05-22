package br.com.noke.twogether.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.noke.twogether.R
import br.com.noke.twogether.model.User
import br.com.noke.twogether.viewmodel.UserViewModel

@Composable
fun CadastroScreen(viewModel: UserViewModel, navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var celular by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmacao by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<List<String>>(emptyList()) }

    // Função para validar os campos
    fun validateFields(): List<String> {
        val errors = mutableListOf<String>()
        val nameIsValid = nome.isNotBlank()
        val emailIsValid = email.isNotBlank() && "@" in email
        val phoneIsValid = celular.isNotBlank() && celular.all { it.isDigit() }
        val passwordsMatch = senha == confirmacao
        val allFieldsFilled = nome.isNotBlank() && email.isNotBlank() && celular.isNotBlank() && senha.isNotBlank() && confirmacao.isNotBlank()

        if (!allFieldsFilled) {
            errors.add("Favor preencha todos os campos.")
        } else {
            if (!emailIsValid) {
                errors.add("Utilize um e-mail válido.")
            }
            if (!phoneIsValid) {
                errors.add("Utilize somente números no celular.")
            }
            if (!passwordsMatch) {
                errors.add("Confirme a senha correta.")
            }
        }
        return errors
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()) {
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
                        .size(150.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
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

            Column(modifier = Modifier.weight(1f, fill = true)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = {
                        nome = it
                        errorMessage = validateFields()
                    },
                    label = { TextWithIcon("Seu nome", Icons.Outlined.AccountCircle) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        errorMessage = validateFields()
                    },
                    label = { TextWithIcon("Seu e-mail", Icons.Filled.Email) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = celular,
                    onValueChange = {
                        celular = it
                        errorMessage = validateFields()
                    },
                    label = { TextWithIcon("Seu número de celular", Icons.Filled.Call) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = senha,
                    onValueChange = {
                        senha = it
                        errorMessage = validateFields()
                    },
                    label = { TextWithIcon("Senha numérica de 8 dígitos!", Icons.Outlined.Lock) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = confirmacao,
                    onValueChange = {
                        confirmacao = it
                        errorMessage = validateFields()
                    },
                    label = { TextWithIcon("Confirme a senha", Icons.Outlined.Lock) },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                        errorMessage.forEach { message ->
                            Text(
                                text = message,
                                color = Color.Red,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Button(
                onClick = {
                    errorMessage = validateFields()
                    if (errorMessage.isEmpty()) {
                        viewModel.addUser(
                            User(
                                nome = nome,
                                email = email,
                                celular = celular
                            )
                        )
                        navController.navigate("categoria")
                    }
                },
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 60.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A5D8))
            ) {
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