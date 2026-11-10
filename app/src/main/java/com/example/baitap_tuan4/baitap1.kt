package com.example.baitap_tuan4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OnboardingItem(val title: String, val description: String, val icon: ImageVector, val buttonText: String)

object OnboardingRepository {
    val items = listOf(
        OnboardingItem("Mọi việc, gọn trong một chỗ", "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.", Icons.Default.List, "Tiếp tục"),
        OnboardingItem("Chia theo Học tập và Cuộc sống", "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần làm gì.", Icons.Default.CheckCircle, "Tiếp tục"),
        OnboardingItem("Nhắc đúng lúc", "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.", Icons.Default.Notifications, "Bắt đầu ngay")
    )
}

@Composable
fun BT1_StartScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1500)
        navController.navigate("bt1_onboarding")
    }
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF3355FF)), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(80.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Text("UTH Todo", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Việc học, việc nhà - gọn gàng mỗi ngày", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
fun BT1_OnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { OnboardingRepository.items.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF3355FF))
                Spacer(modifier = Modifier.width(8.dp))
                Text("UTH Todo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            TextButton(onClick = { navController.navigate("menu") { popUpTo("bt1_start") { inclusive = true } } }) {
                Text("Bỏ qua", color = Color.Gray)
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val item = OnboardingRepository.items[page]
            Column(modifier = Modifier.fillMaxSize().padding(top = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(200.dp).background(Color(0xFFF0F4FF), RoundedCornerShape(24.dp)), contentAlignment = Alignment.Center) {
                    Icon(item.icon, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color(0xFF3355FF))
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(item.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text(item.description, fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalArrangement = Arrangement.Center) {
            repeat(OnboardingRepository.items.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF3355FF) else Color.LightGray
                Box(modifier = Modifier.padding(4.dp).size(if (pagerState.currentPage == iteration) 24.dp else 8.dp, 8.dp).background(color, RoundedCornerShape(50)))
            }
        }

        Button(
            onClick = {
                if (pagerState.currentPage == OnboardingRepository.items.size - 1) {
                    navController.navigate("menu") { popUpTo("bt1_start") { inclusive = true } }
                } else {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3355FF)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(OnboardingRepository.items[pagerState.currentPage].buttonText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}