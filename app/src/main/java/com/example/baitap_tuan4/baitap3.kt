package com.example.baitap_tuan4

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

val BT3_Blue = Color(0xFF4A90E2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BT3_BaseScreen(title: String, subtitle: String, navController: NavController, content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BT3_Blue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("UTH", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E8B57))
            Text("UNIVERSITY OF TRANSPORT\nHOCHIMINH CITY", fontSize = 8.sp, textAlign = TextAlign.Center, color = Color(0xFFD32F2F))
            Spacer(modifier = Modifier.height(16.dp))
            Text("SmartTasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = BT3_Blue)
            Spacer(modifier = Modifier.height(32.dp))
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(subtitle, fontSize = 14.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            content()
        }
    }
}

@Composable
fun BT3_ForgetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    BT3_BaseScreen("Forget Password?", "Enter your Email, we will send you a verification code.", navController) {
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Your Email") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { if (email.isNotBlank()) navController.navigate("bt3_verify/$email") }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = BT3_Blue)) {
            Text("Next")
        }
    }
}

@Composable
fun BT3_VerificationScreen(navController: NavController, email: String?) {
    var otp by remember { mutableStateOf("") }
    BT3_BaseScreen("Verify Code", "Enter the code we just sent you on your registered Email", navController) {
        OutlinedTextField(value = otp, onValueChange = { if (it.length <= 6) otp = it }, label = { Text("Enter OTP (6 digits)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { if (otp.isNotBlank()) navController.navigate("bt3_reset/$email/$otp") }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = BT3_Blue)) {
            Text("Next")
        }
    }
}

@Composable
fun BT3_ResetPasswordScreen(navController: NavController, email: String?, otp: String?) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    BT3_BaseScreen("Create new password", "Your new password must be different from previously used password", navController) {
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirm Password") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            if (password.isNotBlank() && password == confirmPassword) navController.navigate("bt3_confirm/$email/$otp/$password")
            else Toast.makeText(context, "Mật khẩu không khớp hoặc bị trống!", Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = BT3_Blue)) {
            Text("Next")
        }
    }
}

@Composable
fun BT3_ConfirmScreen(navController: NavController, email: String?, otp: String?, password: String?) {
    BT3_BaseScreen("Confirm", "We are here to help you!", navController) {
        OutlinedTextField(value = email ?: "", onValueChange = {}, readOnly = true, label = { Text("Email") }, leadingIcon = { Icon(Icons.Default.Person, null) }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = otp ?: "", onValueChange = {}, readOnly = true, label = { Text("OTP") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password ?: "", onValueChange = {}, readOnly = true, label = { Text("Password") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // YÊU CẦU: Submit xong quay ngược lại màn hình đầu tiên (Forget Password)
                // Dùng popUpTo để xóa các màn hình trung gian khỏi lịch sử (Backstack)
                navController.navigate("bt3_forget") {
                    popUpTo("bt3_forget") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BT3_Blue)
        ) {
            Text("Submit")
        }
    }
}