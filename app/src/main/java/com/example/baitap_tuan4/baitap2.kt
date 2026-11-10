package com.example.baitap_tuan4

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// OOP: Trừu tượng & Kế thừa
interface Borrowable { fun getDisplayInfo(): String }
abstract class Person(val id: String, var name: String)
class Student(id: String, name: String) : Person(id, name) {
    // OOP: Đóng gói
    private val _borrowedBooks = mutableListOf<Book>()
    val borrowedBooks: List<Book> get() = _borrowedBooks
    fun borrowBook(book: Book) { if (!book.isBorrowed) { book.isBorrowed = true; _borrowedBooks.add(book) } }
}
open class Book(val id: String, val title: String) : Borrowable {
    var isBorrowed: Boolean = false
    // OOP: Đa hình
    override fun getDisplayInfo(): String = "Sách giấy: $title - ${if (isBorrowed) "Đã mượn" else "Còn trống"}"
}
class Ebook(id: String, title: String, val fileSize: String) : Book(id, title) {
    override fun getDisplayInfo(): String = "Ebook: $title ($fileSize) - ${if (isBorrowed) "Đã mượn" else "Còn trống"}"
}
object LibraryManager {
    val students = mutableListOf(Student("SV01", "Nguyen Van A"), Student("SV02", "Tran Thi B"))
    val books = mutableListOf<Book>(Book("B01", "Sách 01"), Book("B02", "Sách 02"), Ebook("B03", "Sách 03", "5MB"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BT2_LibraryScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedStudentIndex by remember { mutableIntStateOf(0) }
    val currentStudent = LibraryManager.students[selectedStudentIndex]
    var selectedBookIds by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hệ thống\nQuản lý Thư viện", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(icon = { Icon(Icons.Default.Home, null) }, label = { Text("Quản lý") }, selected = true, onClick = { })
                NavigationBarItem(icon = { Icon(Icons.Default.List, null) }, label = { Text("DS Sách") }, selected = false, onClick = { })
                NavigationBarItem(icon = { Icon(Icons.Default.Person, null) }, label = { Text("Sinh viên") }, selected = false, onClick = { })
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Sinh viên", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(value = currentStudent.name, onValueChange = {}, readOnly = true, modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { selectedStudentIndex = (selectedStudentIndex + 1) % LibraryManager.students.size; selectedBookIds = emptySet() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E50A2))) {
                    Text("Thay đổi")
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Danh sách sách", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp)).padding(16.dp)) {
                LazyColumn {
                    items(LibraryManager.books) { book ->
                        val isAvailable = !book.isBorrowed
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).background(Color.White, RoundedCornerShape(24.dp)).padding(horizontal = 12.dp, vertical = 4.dp)) {
                            Checkbox(
                                checked = selectedBookIds.contains(book.id),
                                onCheckedChange = { if (isAvailable) selectedBookIds = if (it) selectedBookIds + book.id else selectedBookIds - book.id },
                                enabled = isAvailable, colors = CheckboxDefaults.colors(checkedColor = Color(0xFFD32F2F))
                            )
                            Text(book.title, color = if (isAvailable) Color.Black else Color.Gray)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (selectedBookIds.isEmpty()) Toast.makeText(context, "Vui lòng chọn sách!", Toast.LENGTH_SHORT).show()
                    else {
                        selectedBookIds.forEach { id -> LibraryManager.books.find { it.id == id }?.let { currentStudent.borrowBook(it) } }
                        Toast.makeText(context, "Đã mượn sách cho ${currentStudent.name}!", Toast.LENGTH_SHORT).show()
                        selectedBookIds = emptySet()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E50A2)), shape = RoundedCornerShape(8.dp)
            ) { Text("Thêm", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
        }
    }
}