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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitrenlop.data.model.Task
import com.example.baitrenlop.data.model.UserProfile
import com.example.baitrenlop.ui.components.BottomNavBar
import com.example.baitrenlop.ui.components.CategoryRow
import com.example.baitrenlop.ui.components.ProgressCard
import com.example.baitrenlop.ui.components.TaskItemCard
import com.example.baitrenlop.ui.theme.BackgroundLight
import com.example.baitrenlop.ui.theme.TextPrimary
import com.example.baitrenlop.ui.theme.TextSecondary
import com.example.baitrenlop.ui.theme.UthBlue
import com.example.baitrenlop.ui.theme.UthBlueBg
import com.example.baitrenlop.ui.viewmodel.TaskViewModel

@Composable
fun HomeScreen(
    user: UserProfile?,
    taskViewModel: TaskViewModel,
    onNavigate: (String) -> Unit,
    onOpenTaskDetail: (Task) -> Unit,
    onOpenProfile: () -> Unit,
    onAddClick: () -> Unit
) {
    val tasks by taskViewModel.tasks.collectAsState()
    val isLoading by taskViewModel.isLoading.collectAsState()
    val selectedCategory by taskViewModel.selectedCategory.collectAsState()

    val completedCount = tasks.count { it.done }
    val totalCount = tasks.size

    val studyRemainingCount = tasks.count { it.category.lowercase() in listOf("study", "học tập") && !it.done }
    val lifeRemainingCount = tasks.count { it.category.lowercase() in listOf("life", "cuộc sống") && !it.done }

    val todayTasks = tasks.filter { task ->
        selectedCategory == "Tất cả" || task.categoryDisplayName.equals(selectedCategory, ignoreCase = true)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = "home",
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
            // Top Bar Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar + Greeting
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.clickable { onOpenProfile() }
                ) {
                    val initials = user?.name?.split(" ")?.takeLast(2)?.mapNotNull { it.firstOrNull() }?.joinToString("") ?: "K"
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(UthBlueBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initials,
                            fontWeight = FontWeight.Bold,
                            color = UthBlue,
                            fontSize = 16.sp
                        )
                    }

                    Column {
                        Text(
                            text = "Thứ Ba, 20 tháng 10",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = "Chào ${user?.name ?: "bạn"}!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }

                // Notification Bell Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { onNavigate("notifications") },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Thông báo",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Progress Card
                item {
                    ProgressCard(
                        completedCount = completedCount,
                        totalCount = totalCount
                    )
                }

                // Category Row
                item {
                    CategoryRow(
                        studyRemainingCount = studyRemainingCount,
                        lifeRemainingCount = lifeRemainingCount,
                        selectedCategory = selectedCategory,
                        onCategorySelect = { taskViewModel.setSelectedCategory(it) }
                    )
                }

                // Section Header: "Việc hôm nay", "Xem tất cả"
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Việc hôm nay",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Xem tất cả",
                            fontSize = 13.sp,
                            color = UthBlue,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { onNavigate("list") }
                        )
                    }
                }

                // Loading state indicator
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = UthBlue)
                        }
                    }
                }

                // Task List Items
                items(todayTasks, key = { it.id }) { task ->
                    TaskItemCard(
                        task = task,
                        onToggleDone = { taskViewModel.toggleTaskDone(task.id) },
                        onClick = { onOpenTaskDetail(task) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
