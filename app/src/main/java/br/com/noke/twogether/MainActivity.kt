package br.com.noke.twogether


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.noke.twogether.factory.ViewModelFactory
import br.com.noke.twogether.repository.UserRepository
import br.com.noke.twogether.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.noke.twogether.screens.CadastroScreen
import br.com.noke.twogether.screens.CategoriaScreen
import br.com.noke.twogether.screens.ListagemScreen
import br.com.noke.twogether.screens.LoginScreen
import br.com.noke.twogether.ui.theme.TwogetherTheme


//class MainActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        MobileAds.initialize(this)
//
//        // Criação do UserRepository
//        val userRepository = UserRepository(FirebaseFirestore.getInstance())
//        // Criação da Factory
//        val factory = ViewModelFactory(userRepository)
//
//        // Obtenção do ViewModel
//        val viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
//
//        setContent {
//            CadastroScreen(viewModel)
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwogetherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Criação do UserRepository e da Factory
                    val userRepository = UserRepository(FirebaseFirestore.getInstance())
                    val factory = ViewModelFactory(userRepository)

                    // Obtenção do ViewModel
                    val viewModel: UserViewModel = viewModel(factory = factory)

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        exitTransition = {
                            slideOutHorizontally(animationSpec = tween(durationMillis = 2000)) + fadeOut(
                                animationSpec = tween(1000)
                            )
                        },
                        enterTransition = {
                            slideInVertically(animationSpec = tween(durationMillis = 2000)) + fadeIn(
                                animationSpec = tween(1000)
                            )
                        }
                    ) {
                        composable(route = "login") { LoginScreen(navController) }
                        composable(route = "cadastro") { CadastroScreen(viewModel, navController) }
                        composable(route = "categoria") { CategoriaScreen(viewModel, navController) }
                        composable(route = "listagem") { ListagemScreen(viewModel, navController) }



                    }

                }
            }
        }
    }
}









