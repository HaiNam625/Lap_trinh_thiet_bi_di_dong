package com.example.baitrenlop

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.baitrenlop.ui.components.AddTaskDialog
import com.example.baitrenlop.ui.screens.HomeScreen
import com.example.baitrenlop.ui.screens.LoginScreen
import com.example.baitrenlop.ui.screens.NotificationsScreen
import com.example.baitrenlop.ui.screens.ProfileScreen
import com.example.baitrenlop.ui.screens.TaskDetailScreen
import com.example.baitrenlop.ui.screens.TaskListScreen
import com.example.baitrenlop.ui.theme.BaitrenlopTheme
import com.example.baitrenlop.ui.viewmodel.AuthUiMessage
import com.example.baitrenlop.ui.viewmodel.AuthViewModel
import com.example.baitrenlop.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaitrenlopTheme {
                UthSmartTasksApp()
            }
        }
    }
}

@Composable
fun UthSmartTasksApp(
    authViewModel: AuthViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel()
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val currentUser by authViewModel.currentUser.collectAsState()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    val tasks by taskViewModel.tasks.collectAsState()
    val selectedTask by taskViewModel.selectedTask.collectAsState()

    var showAddTaskDialog by remember { mutableStateOf(false) }

    val startDestination = if (isLoggedIn) "home" else "login"

    // Listen to Auth ViewModel Feedback Messages (Toast & Snackbar)
    LaunchedEffect(Unit) {
        authViewModel.uiMessage.collectLatest { message ->
            when (message) {
                is AuthUiMessage.Success -> {
                    Toast.makeText(context, message.message, Toast.LENGTH_SHORT).show()
                    scope.launch { snackbarHostState.showSnackbar(message.message) }
                }
                is AuthUiMessage.Error -> {
                    Toast.makeText(context, message.message, Toast.LENGTH_LONG).show()
                    scope.launch { snackbarHostState.showSnackbar("Lỗi: ${message.message}") }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                // 1. Login Screen
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onLoginWithEmail = { email, pass ->
                            authViewModel.signInWithEmail(email, pass) {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        },
                        onLoginWithGoogle = {
                            authViewModel.signInWithGoogle {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        },
                        onLoginAsGuest = {
                            authViewModel.signInAsGuest {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        }
                    )
                }

                // 2. Home Screen
                composable("home") {
                    HomeScreen(
                        user = currentUser,
                        taskViewModel = taskViewModel,
                        onNavigate = { route -> navController.navigate(route) },
                        onOpenTaskDetail = { task ->
                            taskViewModel.selectTask(task)
                            navController.navigate("detail")
                        },
                        onOpenProfile = { navController.navigate("profile") },
                        onAddClick = { showAddTaskDialog = true }
                    )
                }

                // 3. Task List Screen
                composable("list") {
                    TaskListScreen(
                        taskViewModel = taskViewModel,
                        onNavigate = { route -> navController.navigate(route) },
                        onOpenTaskDetail = { task ->
                            taskViewModel.selectTask(task)
                            navController.navigate("detail")
                        },
                        onAddClick = { showAddTaskDialog = true }
                    )
                }

                // 4. Task Detail Screen
                composable("detail") {
                    selectedTask?.let { task ->
                        TaskDetailScreen(
                            task = task,
                            onBack = { navController.popBackStack() },
                            onToggleDone = { taskId -> taskViewModel.toggleTaskDone(taskId) },
                            onDeleteTask = { taskId ->
                                taskViewModel.deleteTask(taskId) {
                                    Toast.makeText(context, "Đã xóa công việc", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                            },
                            onAddSubtask = { taskId, title ->
                                taskViewModel.addSubtask(taskId, title)
                            },
                            onToggleSubtask = { taskId, subtaskId, done ->
                                taskViewModel.toggleSubtaskDone(taskId, subtaskId, done)
                            },
                            onDeleteSubtask = { taskId, subtaskId ->
                                taskViewModel.deleteSubtask(taskId, subtaskId)
                            }
                        )
                    } ?: LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }

                // 5. Profile Screen
                composable("profile") {
                    ProfileScreen(
                        user = currentUser,
                        completedTaskCount = tasks.count { it.done },
                        pendingTaskCount = tasks.count { !it.done },
                        onNavigate = { route -> navController.navigate(route) },
                        onSignOut = {
                            authViewModel.signOut {
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        },
                        onAddClick = { showAddTaskDialog = true }
                    )
                }

                // 6. Notifications Screen
                composable("notifications") {
                    NotificationsScreen(
                        onNavigate = { route -> navController.navigate(route) },
                        onAddClick = { showAddTaskDialog = true }
                    )
                }
            }

            // Add Task Dialog
            if (showAddTaskDialog) {
                AddTaskDialog(
                    onDismiss = { showAddTaskDialog = false },
                    onTaskCreated = { newTask ->
                        taskViewModel.addTask(newTask) {
                            Toast.makeText(context, "Đã tạo công việc thành công!", Toast.LENGTH_SHORT).show()
                            showAddTaskDialog = false
                        }
                    }
                )
            }
        }
    }
}
