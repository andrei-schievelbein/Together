package br.com.noke.twogether.screens.common

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.noke.notificationpush.notification.NotificationPush
import br.com.noke.twogether.R
import kotlinx.coroutines.delay

//class MainActivity : ComponentActivity() {
//
//    private lateinit var notificationHelper: NotificationPush
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (!isGranted) {
//                Toast.makeText(this, "Permissão de notificação não concedida.", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        notificationHelper = NotificationPush(this, requestPermissionLauncher)
//        notificationHelper.createNotificationChannel()
//        notificationHelper.requestNotificationPermission()
//
//        setContent {
//            NotificationPushTheme {
//                MainScreen(notificationHelper)
//            }
//        }
//    }
//}

//@Composable
//fun Notification(notificationHelper: NotificationPush, title: String, text: String) {
//    notificationHelper.sendNotification(title, text)
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(notificationHelper: NotificationPush) {
    var followCount1 by remember { mutableStateOf(0) }
    var followCount2 by remember { mutableStateOf(0) }
    var followCount3 by remember { mutableStateOf(0) }

    var displayCount1 by remember { mutableStateOf(0) }
    var displayCount2 by remember { mutableStateOf(0) }
    var displayCount3 by remember { mutableStateOf(0) }

    var showNotificationCard by remember { mutableStateOf(false) }

    LaunchedEffect(followCount1) {
        if (followCount1 > 0) {
            delay(4000)
            displayCount1 = followCount1
            val totalFollowers = displayCount1 + displayCount2 + displayCount3
            notificationHelper.sendNotification("Notificação de Mentores", "Você tem $totalFollowers mentor(es) novo(s).")
        } else {
            displayCount1 = 0
        }
    }

    LaunchedEffect(followCount2) {
        if (followCount2 > 0) {
            delay(4000)
            displayCount2 = followCount2
            val totalFollowers = displayCount1 + displayCount2 + displayCount3
            notificationHelper.sendNotification("Notificação de Mentores", "Você tem $totalFollowers mentor(es) novo(s).")
        } else {
            displayCount2 = 0
        }
    }

    LaunchedEffect(followCount3) {
        if (followCount3 > 0) {
            delay(4000)
            displayCount3 = followCount3
            val totalFollowers = displayCount1 + displayCount2 + displayCount3
            notificationHelper.sendNotification("Notificação de Mentores", "Você tem $totalFollowers mentor(es) novo(s).")
        } else {
            displayCount3 = 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 8.dp)
            ) {
                // Replace the Image with your logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Imagem do logo",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.weight(1f)) // Spacer to balance

                // Counter to display the number of followed buttons with icon
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { if (displayCount1 + displayCount2 + displayCount3 > 0) showNotificationCard = true } // Show card on click
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sino),
                        contentDescription = "Ícone de sino",
                        modifier = Modifier.size(24.dp)
                    )
                    val totalFollowers = displayCount1 + displayCount2 + displayCount3
                    if (totalFollowers > 0) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = totalFollowers.toString(),
                                        color = Color.White
                                    )
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sino),
                                contentDescription = "Ícone de sino",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Mentores",
                    modifier = Modifier.padding(start = 8.dp, top = 1.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )
                FollowButton(followCount1) { followCount1 = it }
                FollowButton(followCount2) { followCount2 = it }
                FollowButton(followCount3) { followCount3 = it }
            }
        }

        // Show notification card if needed
        if (showNotificationCard) {
            NotificationCard(displayCount1 + displayCount2 + displayCount3, onDismiss = {
                showNotificationCard = false
                displayCount1 = 0
                displayCount2 = 0
                displayCount3 = 0
            })
        }
    }
}

@Composable
fun FollowButton(followCount: Int, onFollowChange: (Int) -> Unit) {
    Button(onClick = {
        onFollowChange(if (followCount == 0) 1 else 0)
    }) {
        Text(if (followCount == 0) "Seguir" else "Seguindo")
    }
}

@Composable
fun NotificationCard(followCount: Int, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { },
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    text = "Olá, você tem $followCount mentores querendo contacta-lo.",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(onClick = onDismiss) {
                        Text("Confira!")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onDismiss() }) {
                        Text("Fechar")
                    }
                }
            }
        }
    }
}



//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MainScreenPreview() {
//    NotificationPushTheme {
//        MainScreen(notificationHelper = NotificationPush(this, {}))
//    }
//}
