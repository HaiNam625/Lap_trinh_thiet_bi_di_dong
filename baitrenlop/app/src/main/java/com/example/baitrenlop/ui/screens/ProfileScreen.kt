package com.example.baitrenlop.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitrenlop.data.model.UserProfile
import com.example.baitrenlop.ui.components.BottomNavBar
import com.example.baitrenlop.ui.theme.BackgroundLight
import com.example.baitrenlop.ui.theme.TextPrimary
import com.example.baitrenlop.ui.theme.TextSecondary
import com.example.baitrenlop.ui.theme.UthBlue
import com.example.baitrenlop.ui.theme.UthBlueBg
import com.example.baitrenlop.ui.theme.UthGreen
import com.example.baitrenlop.ui.theme.UthRed

@Composable
fun ProfileScreen(
    user: UserProfile?,
    completedTaskCount: Int,
    pendingTaskCount: Int,
    onNavigate: (String) -> Unit,
    onSignOut: () -> Unit,
    onAddClick: () -> Unit
) {
    val totalCount = completedTaskCount + pendingTaskCount
    val completionPercent = if (totalCount > 0) (completedTaskCount * 100 / totalCount) else 0

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = "profile",
                onNavigate = onNavigate,
                onAddClick = onAddClick
            )
        },
        containerColor = BackgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Top Bar: "< Cài đặt", "Hồ sơ"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onNavigate("home") }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Cài đặt",
                        tint = UthBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Cài đặt", color = UthBlue, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text("Hồ sơ", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)

                Spacer(modifier = Modifier.weight(1f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Avatar
                val initials = user?.name?.split(" ")?.takeLast(2)?.mapNotNull { it.firstOrNull() }?.joinToString("") ?: "MA"
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(UthBlueBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = initials,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = UthBlue
                    )
                }

                // Name & Email
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = user?.name ?: "Minh Anh",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = user?.email ?: "minhanh@example.com",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Link badge "✓ Đã liên kết tài khoản Google"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFECFDF5))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = UthGreen,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (user?.isGoogleLinked == true) "Đã liên kết tài khoản Google" else "Tài khoản UTH Todo",
                                fontSize = 12.sp,
                                color = UthGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Stats Card (Done, Pending, Percentage)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$completedTaskCount",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text("Việc đã xong", fontSize = 12.sp, color = TextSecondary)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$pendingTaskCount",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text("Đang chờ", fontSize = 12.sp, color = TextSecondary)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$completionPercent%",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = UthBlue
                            )
                            Text("Hoàn thành", fontSize = 12.sp, color = TextSecondary)
                        }
                    }
                }

                // Bar Chart Section ("Tuần này", "Việc xong mỗi ngày")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tuần này", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Việc xong mỗi ngày", fontSize = 12.sp, color = TextSecondary)
                        }

                        // Bar Chart Graphic (T2 to CN)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            val days = listOf("T2" to 0.4f, "T3" to 0.7f, "T4" to 0.3f, "T5" to 0.5f, "T6" to 0.2f, "T7" to 0.6f, "CN" to 0.1f)
                            days.forEach { (day, heightFraction) ->
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Bottom
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(16.dp)
                                            .height((80 * heightFraction).dp)
                                            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                            .background(if (day == "T3") UthBlue else UthBlueBg)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(day, fontSize = 11.sp, color = TextSecondary)
                                }
                            }
                        }
                    }
                }

                // Sign Out Button
                OutlinedButton(
                    onClick = { onSignOut() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Đăng xuất",
                        tint = UthRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Đăng xuất", color = UthRed, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
