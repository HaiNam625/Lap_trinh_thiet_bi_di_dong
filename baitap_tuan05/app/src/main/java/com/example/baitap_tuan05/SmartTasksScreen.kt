package com.example.baitap_tuan05

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

// --- Data Models ---
data class SubTask(
    val id: String,
    var title: String,
    var isDone: Boolean = false
)

data class TaskModel(
    val id: Int,
    var title: String,
    var category: String, // "Học tập", "Cuộc sống"
    var time: String,     // "Hôm nay, 14:00"
    var isDone: Boolean = false,
    var priority: String = "Vừa", // "Thấp", "Vừa", "Cao"
    var note: String = "",
    var reminder: String = "Trước 15 phút",
    var subtasks: MutableList<SubTask> = mutableListOf()
)

data class NotificationModel(
    val id: String,
    val title: String,
    val description: String,
    val time: String,
    val type: String, // "upcoming", "overdue", "success", "reminder"
    var isRead: Boolean = false
)

// --- ViewModel following MVVM architecture ---
class SmartTasksViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<TaskModel>().apply {
        addAll(
            listOf(
                TaskModel(1, "Tập thể dục 30 phút", "Cuộc sống", "Hôm nay, 06:30", true, "Thấp", "Chạy bộ công viên gần nhà."),
                TaskModel(2, "Nộp bài tập Lập trình di động", "Học tập", "Hôm nay, 09:00", true, "Cao", "Nộp file .zip lên hệ thống E-learning."),
                TaskModel(3, "Ôn chương 3 – Cấu trúc dữ liệu", "Học tập", "Hôm nay, 14:00", false, "Vừa", "Tập trung phần cây nhị phân và bảng băm.", subtasks = mutableListOf(SubTask("s1", "Đọc lại slide bài giảng", true), SubTask("s2", "Làm 10 bài tập cuối chương", false))),
                TaskModel(4, "Đi chợ mua đồ cho tuần", "Cuộc sống", "Hôm nay, 17:30", false, "Thấp", "Mua rau, thịt gà và trái cây."),
                TaskModel(5, "Gọi điện về nhà", "Cuộc sống", "Ngày mai, 21:00", false, "Vừa", "Hỏi thăm sức khỏe bố mẹ."),
                TaskModel(6, "Họp nhóm đồ án", "Học tập", "Tuần này, 20:00", false, "Cao", "Phân chia công việc làm báo cáo.")
            )
        )
    }
    val tasks: List<TaskModel> get() = _tasks

    private val _notifications = mutableStateListOf<NotificationModel>().apply {
        addAll(
            listOf(
                NotificationModel("1", "Sắp đến hạn", "Ôn chương 3 - Cấu trúc dữ liệu lúc 14:00.", "13:45", "upcoming", false),
                NotificationModel("2", "Đã quá hạn", "Bài đọc tham khảo môn Mạng máy tính chưa xong.", "10:10", "overdue", false),
                NotificationModel("3", "Làm tốt lắm!", "Bạn đã xong 2 việc trong sáng nay.", "11:30", "success", true),
                NotificationModel("4", "Nhắc việc", "Họp nhóm đồ án trong tuần này.", "Hôm qua", "reminder", true)
            )
        )
    }
    val notifications: List<NotificationModel> get() = _notifications

    init {
        // Thực hành 1 requirement: Sau 10 giây, cho danh sách về rỗng -> Màn hình hiển thị giao diện emptyView
        viewModelScope.launch {
            delay(10.seconds)
            _notifications.clear()
        }
    }

    fun reloadNotifications() {
        _notifications.clear()
        _notifications.addAll(
            listOf(
                NotificationModel("1", "Sắp đến hạn", "Ôn chương 3 - Cấu trúc dữ liệu lúc 14:00.", "13:45", "upcoming", false),
                NotificationModel("2", "Đã quá hạn", "Bài đọc tham khảo môn Mạng máy tính chưa xong.", "10:10", "overdue", false),
                NotificationModel("3", "Làm tốt lắm!", "Bạn đã xong 2 việc trong sáng nay.", "11:30", "success", true),
                NotificationModel("4", "Nhắc việc", "Họp nhóm đồ án trong tuần này.", "Hôm qua", "reminder", true)
            )
        )
    }

    fun markAllNotificationsRead() {
        for (i in _notifications.indices) {
            _notifications[i] = _notifications[i].copy(isRead = true)
        }
    }

    fun addTask(title: String, category: String, time: String, priority: String, note: String, reminder: String) {
        val newId = (_tasks.maxOfOrNull { it.id } ?: 0) + 1
        _tasks.add(0, TaskModel(id = newId, title = title, category = category, time = time, isDone = false, priority = priority, note = note, reminder = reminder))
    }

    fun updateTask(task: TaskModel) {
        val index = _tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            _tasks[index] = task
        }
    }

    fun toggleTask(task: TaskModel) {
        val index = _tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            _tasks[index] = _tasks[index].copy(isDone = !_tasks[index].isDone)
        }
    }

    fun deleteTask(taskId: Int) {
        _tasks.removeIf { it.id == taskId }
    }
}

// --- Main Container Screen for SmartTasks ---
@Composable
fun SmartTasksMainScreen(
    modifier: Modifier = Modifier,
    onBackToMenu: () -> Unit,
    viewModel: SmartTasksViewModel = viewModel()
) {
    var currentTab by remember { mutableStateOf("home") } // "home", "list", "notifications", "settings", "add"
    var selectedTask by remember { mutableStateOf<TaskModel?>(null) }

    // If detail screen is open
    if (selectedTask != null) {
        TaskDetailScreen(
            task = selectedTask!!,
            onBack = { selectedTask = null },
            onToggleTaskStatus = {
                viewModel.toggleTask(selectedTask!!)
                selectedTask = selectedTask!!.copy(isDone = !selectedTask!!.isDone)
            },
            onDeleteTask = {
                viewModel.deleteTask(selectedTask!!.id)
                selectedTask = null
            },
            onUpdateTask = { updated ->
                viewModel.updateTask(updated)
                selectedTask = updated
            }
        )
        return
    }

    // If add task screen is open (BTVN Tuần 04 - Màn hình thêm mới)
    if (currentTab == "add") {
        AddTaskScreen(
            onBack = { currentTab = "home" },
            onSaveTask = { title, category, dateStr, timeStr, priority, note, reminder ->
                val fullTime = "$dateStr, $timeStr"
                viewModel.addTask(title, category, fullTime, priority, note, reminder)
                currentTab = "home"
            }
        )
        return
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = currentTab == "home",
                    onClick = { currentTab = "home" },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Trang chủ") },
                    label = { Text("Trang chủ") }
                )
                NavigationBarItem(
                    selected = currentTab == "list",
                    onClick = { currentTab = "list" },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Danh sách") },
                    label = { Text("Danh sách") }
                )
                // FAB / Add button in bottom nav matching UI
                NavigationBarItem(
                    selected = false,
                    onClick = { currentTab = "add" },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF3F51B5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Thêm mới", tint = Color.White)
                        }
                    },
                    label = { Text("") }
                )
                NavigationBarItem(
                    selected = currentTab == "notifications",
                    onClick = { currentTab = "notifications" },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Thông báo") },
                    label = { Text("Thông báo") }
                )
                NavigationBarItem(
                    selected = currentTab == "settings",
                    onClick = { currentTab = "settings" },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Cài đặt") },
                    label = { Text("Cài đặt") }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                "home" -> SmartTasksHomeScreen(
                    tasks = viewModel.tasks,
                    onTaskClick = { selectedTask = it },
                    onToggleTask = { viewModel.toggleTask(it) },
                    onViewAllClick = { currentTab = "list" },
                    onBackToMenu = onBackToMenu
                )
                "list" -> SmartTasksListScreen(
                    tasks = viewModel.tasks,
                    onTaskClick = { selectedTask = it },
                    onToggleTask = { viewModel.toggleTask(it) }
                )
                "notifications" -> SmartTasksNotificationScreen(
                    notifications = viewModel.notifications,
                    onMarkAllRead = { viewModel.markAllNotificationsRead() },
                    onReloadNotifications = { viewModel.reloadNotifications() },
                    onAddClick = { currentTab = "add" }
                )
                "settings" -> SmartTasksSettingsScreen(onBackToMenu = onBackToMenu)
            }
        }
    }
}

// --- Screen 1: Trang chủ ---
@Composable
fun SmartTasksHomeScreen(
    tasks: List<TaskModel>,
    onTaskClick: (TaskModel) -> Unit,
    onToggleTask: (TaskModel) -> Unit,
    onViewAllClick: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val completedCount = tasks.count { it.isDone }
    val totalCount = tasks.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f
    val percentText = "${(progress * 100).toInt()}%"

    val hocTapCount = tasks.count { it.category == "Học tập" && !it.isDone }
    val cuocSongCount = tasks.count { it.category == "Cuộc sống" && !it.isDone }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onBackToMenu) {
                    Text("< Menu")
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3F51B5)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("U", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Thứ Ba, 20 tháng 10", fontSize = 12.sp, color = Color.Gray)
                    Text("Chào bạn!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }

        // Tiến độ hôm nay Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF3F51B5)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Tiến độ hôm nay", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("$completedCount/$totalCount việc", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Đã hơn nửa chặng, cố lên!", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    }

                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.size(60.dp),
                            color = Color.White,
                            strokeWidth = 6.dp,
                            trackColor = Color.White.copy(alpha = 0.3f),
                        )
                        Text(percentText, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Phân loại Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4FF)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.School, contentDescription = null, tint = Color(0xFF3F51B5))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Học tập", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("$hocTapCount việc còn lại", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF4EC)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Icon(Icons.Default.Home, contentDescription = null, tint = Color(0xFFFF9800))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Cuộc sống", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("$cuocSongCount việc còn lại", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Việc hôm nay", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = onViewAllClick) {
                    Text("Xem tất cả", color = Color(0xFF3F51B5), fontSize = 14.sp)
                }
            }
        }

        items(tasks.take(4)) { task ->
            TaskRowItem(task = task, onTaskClick = onTaskClick, onToggleTask = onToggleTask)
        }
    }
}

// --- Screen 2: Danh sách ---
@Composable
fun SmartTasksListScreen(
    tasks: List<TaskModel>,
    onTaskClick: (TaskModel) -> Unit,
    onToggleTask: (TaskModel) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Tất cả") }

    val filteredTasks = tasks.filter { task ->
        val matchesSearch = task.title.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "Tất cả" || task.category == selectedCategory
        matchesSearch && matchesCategory
    }

    val uncompletedTasks = filteredTasks.filter { !it.isDone }
    val completedTasks = filteredTasks.filter { it.isDone }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Danh sách", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Surface(
                    color = Color(0xFFE8EAF6),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "${uncompletedTasks.size} việc chưa xong",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        color = Color(0xFF3F51B5),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Tìm công việc...") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Tất cả", "Học tập", "Cuộc sống").forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF3F51B5),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        item {
            Text(
                "CẦN LÀM (${uncompletedTasks.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        if (uncompletedTasks.isEmpty()) {
            item {
                Text("Không có công việc nào cần làm.", fontSize = 14.sp, color = Color.Gray)
            }
        } else {
            items(uncompletedTasks) { task ->
                TaskRowItem(task = task, onTaskClick = onTaskClick, onToggleTask = onToggleTask)
            }
        }

        item {
            Text(
                "ĐÃ XONG (${completedTasks.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        if (completedTasks.isEmpty()) {
            item {
                Text("Chưa có công việc nào hoàn thành.", fontSize = 14.sp, color = Color.Gray)
            }
        } else {
            items(completedTasks) { task ->
                TaskRowItem(task = task, onTaskClick = onTaskClick, onToggleTask = onToggleTask)
            }
        }
    }
}

// --- Screen: Thông báo (Thực hành 1 - MVVM & 10s EmptyView) ---
@Composable
fun SmartTasksNotificationScreen(
    notifications: List<NotificationModel>,
    onMarkAllRead: () -> Unit,
    onReloadNotifications: () -> Unit,
    onAddClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Thông báo", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = onReloadNotifications) {
                    Text("Tải lại mẫu", fontSize = 12.sp, color = Color(0xFF3F51B5))
                }
                TextButton(onClick = onMarkAllRead) {
                    Text("Đánh dấu đã đọc", fontSize = 12.sp, color = Color(0xFF3F51B5))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (notifications.isEmpty()) {
            // Empty View matching Image 1 right mockup exactly
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Chưa có thông báo nào",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Khi có việc sắp đến hạn, quá hạn hoặc lời nhắc, bạn sẽ thấy chúng ở đây.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onAddClick,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+ Thêm việc cần làm", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = { Toast.makeText(context, "Mở cài đặt nhắc việc", Toast.LENGTH_SHORT).show() }
                ) {
                    Text("Cài đặt nhắc việc", color = Color(0xFF3F51B5), fontSize = 14.sp)
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notifications) { notif ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (notif.type) {
                                    "upcoming" -> Icons.Default.Schedule
                                    "overdue" -> Icons.Default.Warning
                                    "success" -> Icons.Default.CheckCircle
                                    else -> Icons.Default.Notifications
                                },
                                contentDescription = null,
                                tint = when (notif.type) {
                                    "upcoming" -> Color(0xFF3F51B5)
                                    "overdue" -> Color.Red
                                    "success" -> Color(0xFF4CAF50)
                                    else -> Color.Gray
                                },
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(notif.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text(notif.time, fontSize = 12.sp, color = Color.Gray)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(notif.description, fontSize = 13.sp, color = Color.DarkGray)
                            }
                            if (!notif.isRead) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF3F51B5))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- Screen: BTVN Tuần 04 - Màn hình thêm mới (Image 2 Mockup) ---
@Composable
fun AddTaskScreen(
    onBack: () -> Unit,
    onSaveTask: (title: String, category: String, date: String, time: String, priority: String, note: String, reminder: String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Học tập") }
    var dateSelection by remember { mutableStateOf("Hôm nay") }
    var timeSelection by remember { mutableStateOf("19:00") }
    var reminderEnabled by remember { mutableStateOf(true) }
    var priority by remember { mutableStateOf("Vừa") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        // Top App Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text("Hủy", color = Color.Gray, fontSize = 16.sp)
            }
            Text("Việc mới", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextButton(
                onClick = {
                    if (title.isBlank()) {
                        Toast.makeText(context, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show()
                    } else {
                        onSaveTask(title, category, dateSelection, timeSelection, priority, note, if (reminderEnabled) "Trước 15 phút" else "Không")
                    }
                }
            ) {
                Text("Lưu", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3F51B5))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Tên công việc
            item {
                Column {
                    Text("Tên công việc", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("Ví dụ: Ôn chương 4 – Giải thuật") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Ghi chú
            item {
                Column {
                    Text("Ghi chú", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        placeholder = { Text("Thêm ghi chú (không bắt buộc)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Nhóm
            item {
                Column {
                    Text("Nhóm", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        listOf("Học tập", "Cuộc sống").forEach { cat ->
                            OutlinedButton(
                                onClick = { category = cat },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (category == cat) Color(0xFF3F51B5) else Color.White,
                                    contentColor = if (category == cat) Color.White else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(cat)
                            }
                        }
                    }
                }
            }

            // Ngày
            item {
                Column {
                    Text("Ngày", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Hôm nay", "Ngày mai", "Tuần này").forEach { d ->
                            OutlinedButton(
                                onClick = { dateSelection = d },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (dateSelection == d) Color(0xFF3F51B5) else Color.White,
                                    contentColor = if (dateSelection == d) Color.White else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(d, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Giờ và Nhắc tôi
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF3F51B5))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Giờ", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            }
                            Surface(
                                color = Color(0xFFF0F0F0),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    timeSelection,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF3F51B5))
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Nhắc tôi", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                                    Text("Trước 15 phút", fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                            Switch(
                                checked = reminderEnabled,
                                onCheckedChange = { reminderEnabled = it }
                            )
                        }
                    }
                }
            }

            // Mức ưu tiên
            item {
                Column {
                    Text("Mức ưu tiên", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Thấp", "Vừa", "Cao").forEach { p ->
                            OutlinedButton(
                                onClick = { priority = p },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = if (priority == p) Color(0xFF3F51B5) else Color.White,
                                    contentColor = if (priority == p) Color.White else Color.Black
                                ),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(p)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isBlank()) {
                    Toast.makeText(context, "Vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show()
                } else {
                    onSaveTask(title, category, dateSelection, timeSelection, priority, note, if (reminderEnabled) "Trước 15 phút" else "Không")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Lưu công việc", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// --- Task Row Item Component ---
@Composable
fun TaskRowItem(
    task: TaskModel,
    onTaskClick: (TaskModel) -> Unit,
    onToggleTask: (TaskModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task) },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onToggleTask(task) }) {
                Icon(
                    imageVector = if (task.isDone) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (task.isDone) Color(0xFF3F51B5) else Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else null,
                    color = if (task.isDone) Color.Gray else Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = task.category,
                        fontSize = 12.sp,
                        color = Color(0xFF3F51B5),
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "  |  ${task.time}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            if (task.priority == "Cao" || task.priority == "Gấp") {
                Surface(
                    color = Color(0xFFFFEBEE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Gấp",
                        fontSize = 10.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

// --- Screen 3: Chi tiết công việc ---
@Composable
fun TaskDetailScreen(
    task: TaskModel,
    onBack: () -> Unit,
    onToggleTaskStatus: () -> Unit,
    onDeleteTask: () -> Unit,
    onUpdateTask: (TaskModel) -> Unit
) {
    var newSubtaskText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Trang chủ")
            }
            TextButton(onClick = {
                Toast.makeText(context, "Chức năng chỉnh sửa đang phát triển!", Toast.LENGTH_SHORT).show()
            }) {
                Text("Sửa", color = Color(0xFF3F51B5))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(color = Color(0xFFE8EAF6), shape = RoundedCornerShape(8.dp)) {
                Text(
                    task.category,
                    fontSize = 12.sp,
                    color = Color(0xFF3F51B5),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Surface(color = Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)) {
                Text(
                    "Ưu tiên ${task.priority.lowercase()}",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = task.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Hạn chót", fontSize = 14.sp, color = Color.Gray)
                    }
                    Text(task.time, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF0F0F0))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Nhắc nhở", fontSize = 14.sp, color = Color.Gray)
                    }
                    Text(task.reminder, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Ghi chú", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = task.note.ifEmpty { "Không có ghi chú." },
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val doneSubtasks = task.subtasks.count { it.isDone }
        val totalSubtasks = task.subtasks.size
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Việc nhỏ", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            Text("$doneSubtasks/$totalSubtasks", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(6.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(task.subtasks) { subtask ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = subtask.isDone,
                            onCheckedChange = { isChecked ->
                                subtask.isDone = isChecked
                                onUpdateTask(task.copy())
                            }
                        )
                        Text(
                            text = subtask.title,
                            modifier = Modifier.weight(1f),
                            textDecoration = if (subtask.isDone) TextDecoration.LineThrough else null,
                            color = if (subtask.isDone) Color.Gray else Color.Black
                        )
                        IconButton(onClick = {
                            task.subtasks.remove(subtask)
                            onUpdateTask(task.copy())
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.LightGray, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = newSubtaskText,
                        onValueChange = { newSubtaskText = it },
                        placeholder = { Text("+ Thêm việc nhỏ") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (newSubtaskText.isNotBlank()) {
                                task.subtasks.add(SubTask(System.currentTimeMillis().toString(), newSubtaskText, false))
                                newSubtaskText = ""
                                onUpdateTask(task.copy())
                            }
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Thêm")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onToggleTaskStatus,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (task.isDone) Color.Gray else Color(0xFF3F51B5)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (task.isDone) "Đánh dấu chưa xong" else "Đánh dấu hoàn thành", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onDeleteTask,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Xóa công việc", color = Color.Red, fontSize = 14.sp)
        }
    }
}

// --- Dedicated Screens for separate exercises ---
@Composable
fun ThucHanh1Screen(modifier: Modifier = Modifier, onBackToMenu: () -> Unit) {
    val viewModel: SmartTasksViewModel = viewModel()
    Column(modifier = modifier.fillMaxSize().background(Color(0xFFF8F9FA))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBackToMenu) {
                Text("< Quay lại Menu")
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("Thực hành 1 (Ảnh 1): Thông báo MVVM", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        SmartTasksNotificationScreen(
            notifications = viewModel.notifications,
            onMarkAllRead = { viewModel.markAllNotificationsRead() },
            onReloadNotifications = { viewModel.reloadNotifications() },
            onAddClick = { onBackToMenu() }
        )
    }
}

@Composable
fun Btvn4Screen(modifier: Modifier = Modifier, onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val viewModel: SmartTasksViewModel = viewModel()
    Column(modifier = modifier.fillMaxSize()) {
        AddTaskScreen(
            onBack = onBackToMenu,
            onSaveTask = { title, category, date, time, priority, note, reminder ->
                viewModel.addTask(title, category, "$date, $time", priority, note, reminder)
                Toast.makeText(context, "Đã lưu: $title ($category - $date $time)", Toast.LENGTH_LONG).show()
                onBackToMenu()
            }
        )
    }
}


// --- Screen: Cài đặt ---
@Composable
fun SmartTasksSettingsScreen(onBackToMenu: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Settings, contentDescription = null, tint = Color(0xFF3F51B5), modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Cài đặt UTH SmartTasks", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Phiên bản v1.0 - MVVM Architecture", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onBackToMenu,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Quay lại Menu chính")
        }
    }
}
