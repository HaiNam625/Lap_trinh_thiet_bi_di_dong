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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.baitrenlop.ui.components.BottomNavBar
import com.example.baitrenlop.ui.components.TaskItemCard
import com.example.baitrenlop.ui.theme.BackgroundLight
import com.example.baitrenlop.ui.theme.TextPrimary
import com.example.baitrenlop.ui.theme.TextSecondary
import com.example.baitrenlop.ui.theme.UthBlue
import com.example.baitrenlop.ui.theme.UthBlueBg
import com.example.baitrenlop.ui.viewmodel.TaskViewModel

@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
    onNavigate: (String) -> Unit,
    onOpenTaskDetail: (Task) -> Unit,
    onAddClick: () -> Unit
) {
    val searchQuery by taskViewModel.searchQuery.collectAsState()
    val selectedCategory by taskViewModel.selectedCategory.collectAsState()
    val pendingTasks by taskViewModel.filteredPendingTasks.collectAsState()
    val completedTasks by taskViewModel.filteredCompletedTasks.collectAsState()
    val totalPendingCount by taskViewModel.totalPendingCount.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = "list",
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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Title & Subtitle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Danh sách",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "$totalPendingCount việc chưa xong",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { taskViewModel.setSearchQuery(it) },
                placeholder = { Text("Tìm công việc", fontSize = 14.sp, color = TextSecondary) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Tìm kiếm",
                        tint = TextSecondary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = UthBlue,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Chips ("Tất cả", "Học tập", "Cuộc sống")
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Tất cả", "Học tập", "Cuộc sống").forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) UthBlue else Color.White)
                            .clickable { taskViewModel.setSelectedCategory(cat) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Task Lists (Pending vs Completed)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Section CẦN LÀM
                if (pendingTasks.isNotEmpty()) {
                    item {
                        Text(
                            text = "CẦN LÀM",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    items(pendingTasks, key = { "pending_${it.id}" }) { task ->
                        TaskItemCard(
                            task = task,
                            onToggleDone = { taskViewModel.toggleTaskDone(task.id) },
                            onClick = { onOpenTaskDetail(task) }
                        )
                    }
                }

                // Section ĐÃ XONG
                if (completedTasks.isNotEmpty()) {
                    item {
                        Text(
                            text = "ĐÃ XONG",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )
                    }
                    items(completedTasks, key = { "completed_${it.id}" }) { task ->
                        TaskItemCard(
                            task = task,
                            onToggleDone = { taskViewModel.toggleTaskDone(task.id) },
                            onClick = { onOpenTaskDetail(task) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
