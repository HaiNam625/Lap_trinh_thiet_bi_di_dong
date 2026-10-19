package com.example.baitap01_tuan04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

// ==========================================
// 1. ĐIỀU HƯỚNG (NAVIGATION)
// ==========================================
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(onReadyClick = { navController.navigate("list") })
        }
        composable("list") {
            ComponentListScreen(
                onTextClick = { navController.navigate("text_detail") },
                onImageClick = { navController.navigate("image_detail") },
                onTextFieldClick = { navController.navigate("textfield_detail") },
                onRowLayoutClick = { navController.navigate("row_detail") }
            )
        }
        composable("text_detail") { TextDetailScreen(onBack = { navController.popBackStack() }) }
        composable("image_detail") { ImageDetailScreen(onBack = { navController.popBackStack() }) }
        composable("textfield_detail") { TextFieldDetailScreen(onBack = { navController.popBackStack() }) }
        composable("row_detail") { RowLayoutScreen(onBack = { navController.popBackStack() }) }
    }
}

// ==========================================
// 2. CÁC HÀM DÙNG CHUNG (TOP APP BAR)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color(0xFF2196F3)) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF2196F3)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

// ==========================================
// 3. MÀN HÌNH 1: WELCOME (I'M READY)
// ==========================================
@Composable
fun WelcomeScreen(onReadyClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Đã gọi ảnh Logo Jetpack Compose thật của bạn
        Image(
            painter = painterResource(id = R.drawable.ic_jetpack_logo),
            contentDescription = "Jetpack Compose Logo",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Jetpack Compose", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Jetpack Compose is a modern UI toolkit for building native Android applications using a declarative programming approach.",
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onReadyClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "I'm ready", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 4. MÀN HÌNH 2: DANH SÁCH COMPONENTS
// ==========================================
@Composable
fun ComponentListItem(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFCDE4F9))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
        Text(text = subtitle, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Composable
fun ComponentListScreen(
    onTextClick: () -> Unit,
    onImageClick: () -> Unit,
    onTextFieldClick: () -> Unit,
    onRowLayoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "UI Components List",
            color = Color(0xFF2196F3),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Display", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        ComponentListItem("Text", "Displays text", onTextClick)
        ComponentListItem("Image", "Displays an image", onImageClick)

        Text("Input", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        ComponentListItem("TextField", "Input field for text", onTextFieldClick)
        ComponentListItem("PasswordField", "Input field for passwords") {}

        Text("Layout", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
        ComponentListItem("Column", "Arranges elements vertically") {}
        ComponentListItem("Row", "Arranges elements horizontally", onRowLayoutClick)

        // Nút màu hồng tự tìm hiểu
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF8C8C8))
                .padding(12.dp)
        ) {
            Text("Tự tìm hiểu", fontWeight = FontWeight.Bold, color = Color(0xFF8B0000))
            Text("Tìm ra tất cả các thành phần UI Cơ bản", fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}

// ==========================================
// 5. MÀN HÌNH 3: TEXT DETAIL (RICH TEXT)
// ==========================================
@Composable
fun TextDetailScreen(onBack: () -> Unit) {
    Scaffold(topBar = { CustomTopAppBar("Text Detail", onBack) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            val styledText = buildAnnotatedString {
                append("The quick ")
                withStyle(style = SpanStyle(color = Color(0xFFC68E17), fontWeight = FontWeight.Bold)) { append("Brown") }
                append(" fox jumps ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("over") }
                append(" the ")
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, textDecoration = TextDecoration.Underline)) { append("lazy") }
                append(" dog.")
            }
            Text(text = styledText, fontSize = 24.sp, lineHeight = 36.sp)
        }
    }
}

// ==========================================
// 6. MÀN HÌNH 4: IMAGE DETAIL
// ==========================================
@Composable
fun ImageDetailScreen(onBack: () -> Unit) {
    Scaffold(topBar = { CustomTopAppBar("Images", onBack) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ảnh 1: Logo UTH
            Image(
                painter = painterResource(id = R.drawable.ic_uth_logo),
                contentDescription = "UTH Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "https://diachohvanvantoanhoc.edu.vn/wp-content/uploads/2025/01/Logo-GTVT.png",
                fontSize = 10.sp,
                modifier = Modifier.padding(8.dp)
            )

            // Ảnh 2: Ảnh trường UTH
            Image(
                painter = painterResource(id = R.drawable.ic_uth_school),
                contentDescription = "UTH School",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "In app", color = Color.Gray)
        }
    }
}

// ==========================================
// 7. MÀN HÌNH 5: TEXTFIELD DETAIL
// ==========================================
@Composable
fun TextFieldDetailScreen(onBack: () -> Unit) {
    var textState by remember { mutableStateOf("") }
    Scaffold(topBar = { CustomTopAppBar("TextField", onBack) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                placeholder = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tự động cập nhật dữ liệu theo textfield",
                color = Color(0xFFD32F2F),
                fontSize = 12.sp
            )
        }
    }
}

// ==========================================
// 8. MÀN HÌNH 6: ROW LAYOUT DETAIL
// ==========================================
@Composable
fun RowLayoutScreen(onBack: () -> Unit) {
    Scaffold(topBar = { CustomTopAppBar("Row Layout", onBack) }) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(4) { // Lặp 4 hàng
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFB3D4F0))
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF4F8FF6))
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFB3D4F0))
                    )
                }
            }
        }
    }
}