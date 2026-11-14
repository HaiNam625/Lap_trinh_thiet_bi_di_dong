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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                            onSelectThucHanh1 = { currentScreen = "thuchanh1" },
                            onSelectBtvn4 = { currentScreen = "btvn4" },
                            onSelectBT1 = { currentScreen = "bt1" },
                            onSelectBT2 = { currentScreen = "bt2" },
                            onSelectSmartTasks = { currentScreen = "smarttasks" },
                        )
                        // Thực hành 1 (Ảnh 1): Thông báo MVVM (10s -> EmptyView)
                        "thuchanh1" -> ThucHanh1Screen(
                            modifier = modifierWithPadding,
                            onBackToMenu = { currentScreen = "menu" }
                        )
                        // BTVN Tuần 04 (Ảnh 2): Màn hình Thêm mới công việc
                        "btvn4" -> Btvn4Screen(
                            modifier = modifierWithPadding,
                            onBackToMenu = { currentScreen = "menu" }
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
                        // Gọi đến UTH SmartTasks (Tuần 04)
                        "smarttasks" -> SmartTasksMainScreen(
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
fun MenuScreen(
    modifier: Modifier = Modifier,
    onSelectThucHanh1: () -> Unit,
    onSelectBtvn4: () -> Unit,
    onSelectBT1: () -> Unit,
    onSelectBT2: () -> Unit,
    onSelectSmartTasks: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "UTH SMARTTASKS", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(text = "Chọn bài tập theo yêu cầu", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onSelectThucHanh1,
            modifier = Modifier.fillMaxWidth(0.9f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text("Thực hành 1 (Ảnh 1): Thông báo MVVM (10s -> Rỗng)", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onSelectBtvn4,
            modifier = Modifier.fillMaxWidth(0.9f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
        ) {
            Text("BTVN Tuần 04 (Ảnh 2): Màn hình Thêm mới MVVM", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onSelectSmartTasks,
            modifier = Modifier.fillMaxWidth(0.9f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("UTH SmartTasks (Toàn bộ ứng dụng)", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSelectBT1, modifier = Modifier.fillMaxWidth(0.9f).height(45.dp)) {
            Text("Bài 1 (Cũ): Firebase Authentication")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = onSelectBT2, modifier = Modifier.fillMaxWidth(0.9f).height(45.dp)) {
            Text("Bài 2 (Cũ): Firebase Realtime DB")
        }
    }
}