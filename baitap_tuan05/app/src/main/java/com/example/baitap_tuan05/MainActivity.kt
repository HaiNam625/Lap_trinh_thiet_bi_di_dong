package com.example.baitap_tuan05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.baitap_tuan05.ui.theme.Baitap_tuan05Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Baitap_tuan05Theme {
                var currentScreen by remember { mutableStateOf("menu") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifierWithPadding = Modifier.padding(innerPadding)

                    when (currentScreen) {
                        "menu" -> MenuScreen(
                            modifier = modifierWithPadding,
                            onSelectBT1 = { currentScreen = "bt1" },
                            onSelectBT2 = { currentScreen = "bt2" }
                        )
                        // Gọi đến file baitap1.kt
                        "bt1" -> Baitap1Screen(
                            modifier = modifierWithPadding,
                            onBackToMenu = { currentScreen = "menu" }
                        )
                        // Gọi đến file baitap2.kt
                        "bt2" -> Baitap2Screen(
                            modifier = modifierWithPadding,
                            onBackToMenu = { currentScreen = "menu" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuScreen(modifier: Modifier = Modifier, onSelectBT1: () -> Unit, onSelectBT2: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "CHỌN BÀI TẬP", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onSelectBT1, modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)) {
            Text("Bài 1: Firebase Authentication")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSelectBT2, modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)) {
            Text("Bài 2: Firebase Realtime DB")
        }
    }
}