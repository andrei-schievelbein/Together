package br.com.noke.twogether

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.noke.twogether.notification.NotificationPush
import br.com.noke.twogether.factory.ViewModelFactory
import br.com.noke.twogether.repository.UserRepository
import br.com.noke.twogether.screens.CadastroScreen
import br.com.noke.twogether.screens.CategoriaScreen
import br.com.noke.twogether.screens.ListagemScreen
import br.com.noke.twogether.screens.LoginScreen
import br.com.noke.twogether.screens.MentorScreen
import br.com.noke.twogether.ui.theme.TwogetherTheme
import br.com.noke.twogether.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {

    private lateinit var notificationHelper: NotificationPush

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Configuração da permissão para notificações
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                Toast.makeText(this, "Permissão de notificação não concedida.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Inicialização do NotificationHelper
        notificationHelper = NotificationPush(this, requestPermissionLauncher)
        notificationHelper.createNotificationChannel()
        notificationHelper.requestNotificationPermission()

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
                            slideOutHorizontally(animationSpec = tween(durationMillis = 1000)) + fadeOut(
                                animationSpec = tween(1000)
                            )
                        },
                        enterTransition = {
                            slideInVertically(animationSpec = tween(durationMillis = 1000)) + fadeIn(
                                animationSpec = tween(1000)
                            )
                        }
                    ) {
                        composable(route = "login") { LoginScreen(navController) }
                        composable(route = "cadastro") { CadastroScreen(viewModel, navController) }
                        composable(route = "categoria") {
                            CategoriaScreen(
                                viewModel,
                                navController
                            )
                        }
                        composable(route = "listagem") {
                            ListagemScreen(
                                viewModel,
                                navController,
                                notificationHelper
                            )
                        }
                        composable(
                            "mentor/{encodedUserJson}",
                            arguments = listOf(navArgument("encodedUserJson") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val encodedUserJson =
                                backStackEntry.arguments?.getString("encodedUserJson") ?: ""
                            MentorScreen(encodedUserJson, navController)
                        }
                    }
                }
            }
        }
    }
}









