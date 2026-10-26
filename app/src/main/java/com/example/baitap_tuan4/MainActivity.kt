package com.example.baitap_tuan4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "menu") {
        // --- 1. MÀN HÌNH MENU ---
        composable("menu") { MainMenuScreen(navController) }

        // --- 2. CÁC MÀN HÌNH BÀI 1 ---
        composable("bt1_start") { BT1_StartScreen(navController) }
        composable("bt1_onboarding") { BT1_OnboardingScreen(navController) }

        // --- 3. MÀN HÌNH BÀI 2 ---
        composable("bt2_library") { BT2_LibraryScreen(navController) }

        // --- 4. CÁC MÀN HÌNH BÀI 3 (Truyền dữ liệu) ---
        composable("bt3_forget") { BT3_ForgetPasswordScreen(navController) }

        composable("bt3_verify/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            BT3_VerificationScreen(navController, email)
        }

        composable("bt3_reset/{email}/{otp}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val otp = backStackEntry.arguments?.getString("otp")
            BT3_ResetPasswordScreen(navController, email, otp)
        }

        composable("bt3_confirm/{email}/{otp}/{password}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val otp = backStackEntry.arguments?.getString("otp")
            val password = backStackEntry.arguments?.getString("password")
            BT3_ConfirmScreen(navController, email, otp, password)
        }
    }
}

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("DANH SÁCH BÀI TẬP", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { navController.navigate("bt1_start") }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Bài 1: UTH Todo (Navigation cơ bản)", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("bt2_library") }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Bài 2: Quản lý Thư viện (OOP)", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("bt3_forget") }, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Bài 3: Data Flow (Truyền dữ liệu)", fontSize = 16.sp)
        }
    }
}