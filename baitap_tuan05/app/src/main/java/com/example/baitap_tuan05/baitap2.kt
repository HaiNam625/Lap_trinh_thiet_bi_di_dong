package com.example.baitap_tuan05

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*

data class TodoItem(
    val title: String = "",
    val category: String = "",
    val time: String = "",
    val isChecked: Boolean = false
)

@Composable
fun Baitap2Screen(modifier: Modifier = Modifier, onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val database = FirebaseDatabase.getInstance()
    // Đường dẫn lưu dữ liệu trên Realtime DB
    val myRef = database.getReference("tasks")
    var taskList by remember { mutableStateOf<List<TodoItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Tự động đẩy dữ liệu mẫu lên Firebase nếu Database đang trống
    LaunchedEffect(Unit) {
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    val sampleTasks = listOf(
                        TodoItem("Học Firebase Realtime", "Học tập", "08:00", false),
                        TodoItem("Tập thể dục", "Cuộc sống", "17:00", true)
                    )
                    myRef.setValue(sampleTasks)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // Lắng nghe dữ liệu thay đổi từ Firebase
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<TodoItem>()
                for (data in snapshot.children) {
                    val item = data.getValue(TodoItem::class.java)
                    if (item != null) items.add(item)
                }
                taskList = items
                isLoading = false
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Lỗi đọc DB: ${error.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
        myRef.addValueEventListener(listener)
        onDispose { myRef.removeEventListener(listener) }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("BÀI 2: DỮ LIỆU TỪ FIREBASE", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (taskList.isEmpty()) {
                Text("Chưa có công việc nào trên Firebase.", color = Color.Gray)
            } else {
                // Tái sử dụng lại giao diện TaskItem ở file baitap1.kt
                taskList.forEach { task ->
                    TaskItem(title = task.title, category = task.category, time = task.time, isChecked = task.isChecked)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBackToMenu, modifier = Modifier.fillMaxWidth().height(50.dp)) {
            Text("Quay lại Menu chính")
        }
    }
}