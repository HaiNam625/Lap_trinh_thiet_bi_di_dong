package com.example.baitap_tuan05

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Baitap1Screen(modifier: Modifier = Modifier, onBackToMenu: () -> Unit) {
    val auth = remember { FirebaseAuth.getInstance() }
    var currentUser by remember { mutableStateOf(auth.currentUser) }
    var isShowProfile by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        if (currentUser == null) {
            LoginScreen(
                onLoginSuccess = { currentUser = auth.currentUser },
                onBackToMenu = onBackToMenu
            )
        } else {
            if (isShowProfile) {
                ProfileScreen(
                    email = currentUser?.email ?: "Người dùng",
                    onLogout = {
                        auth.signOut()
                        currentUser = null
                        isShowProfile = false
                    },
                    onBack = { isShowProfile = false }
                )
            } else {
                HomeScreen(
                    email = currentUser?.email ?: "Bạn",
                    onProfileClick = { isShowProfile = true },
                    onBackToMenu = onBackToMenu
                )
            }
        }
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onBackToMenu: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextButton(onClick = onBackToMenu, modifier = Modifier.align(Alignment.Start)) {
            Text("< Quay lại Menu")
        }

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Chào mừng đến với\nUTH Todo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text("Đăng nhập để lưu công việc và đồng bộ trên mọi thiết bị của bạn.", fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            } else {
                                // Nếu tài khoản chưa tồn tại, tự động đăng ký mới cho nhanh
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { createTask ->
                                        if (createTask.isSuccessful) {
                                            Toast.makeText(context, "Đã tạo tài khoản & Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                                            onLoginSuccess()
                                        } else {
                                            Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Đăng nhập / Đăng ký")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { Toast.makeText(context, "Chức năng Google cần cấu hình SHA-1!", Toast.LENGTH_SHORT).show() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Tiếp tục với Google")
        }
    }
}

@Composable
fun HomeScreen(email: String, onProfileClick: () -> Unit, onBackToMenu: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFF3F51B5)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (email.isNotEmpty()) email.substring(0, 1).uppercase() else "U", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Thứ Ba, 20 tháng 10", fontSize = 12.sp, color = Color.Gray)
                Text("Chào ${email.substringBefore("@")}!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Thẻ tiến độ
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF3F51B5)), modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Tiến độ hôm nay", color = Color.White, fontSize = 14.sp)
                    Text("2/4 việc", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Đã hơn nửa chặng, cố lên!", color = Color.White, fontSize = 12.sp)
                }
                CircularProgressIndicator(
                    progress = { 0.5f },
                    color = Color.White,
                    trackColor = Color.LightGray,
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Phân loại
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CategoryCard(title = "Học tập", count = "2 việc còn lại", modifier = Modifier.weight(1f))
            CategoryCard(title = "Cuộc sống", count = "2 việc còn lại", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Việc hôm nay", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Xem tất cả", color = Color.Blue, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Danh sách công việc tĩnh
        TaskItem(title = "Tập thể dục 30 phút", category = "Cuộc sống", time = "06:30", isChecked = true)
        TaskItem(title = "Nộp bài tập Lập trình di động", category = "Học tập", time = "09:00", isChecked = true)
        TaskItem(title = "Ôn chương 3 - Cấu trúc dữ liệu", category = "Học tập", time = "14:00", isChecked = false)

        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onBackToMenu, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Quay lại Menu chính")
        }
    }
}

@Composable
fun CategoryCard(title: String, count: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.School, contentDescription = null, tint = Color.Blue)
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold)
            Text(count, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

// Hàm này được dùng chung cho cả Bài 1 và Bài 2
@Composable
fun TaskItem(title: String, category: String, time: String, isChecked: Boolean) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isChecked) Color.Blue else Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if(isChecked) TextDecoration.LineThrough else null,
                    color = if(isChecked) Color.Gray else Color.Black
                )
                Text(category, fontSize = 12.sp, color = Color.Blue)
            }
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProfileScreen(email: String, onLogout: () -> Unit, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("< Cài đặt") }
            Spacer(modifier = Modifier.weight(1f))
            Text("Hồ sơ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFE3F2FD)), contentAlignment = Alignment.Center) {
            Text(email.substringBefore("@").uppercase(), color = Color.Blue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(email.substringBefore("@"), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(email, color = Color.Gray, fontSize = 14.sp)
        Text("✓ Đã liên kết tới Firebase", color = Color.Green, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatItem("2", "Việc đã xong")
            StatItem("4", "Đang chờ")
            StatItem("33%", "Hoàn thành")
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color.Red)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Đăng xuất")
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}