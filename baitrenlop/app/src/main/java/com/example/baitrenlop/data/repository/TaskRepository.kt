package com.example.baitrenlop.data.repository

import com.example.baitrenlop.data.model.Subtask
import com.example.baitrenlop.data.model.Task
import com.example.baitrenlop.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskRepository {

    private val initialTasks = listOf(
        Task(
            id = "1",
            title = "Nộp bài tập Lập trình di động",
            note = "Nộp file .zip lên hệ thống của trường. Nhớ kèm ảnh chụp màn hình app.",
            category = "study",
            date = "2026-10-20",
            time = "09:00",
            priority = "high",
            remind = true,
            done = true,
            subtasks = listOf(
                Subtask(id = 0, title = "Hoàn thiện màn hình đăng nhập", done = true),
                Subtask(id = 1, title = "Chụp ảnh màn hình app", done = true),
                Subtask(id = 2, title = "Nén file và nộp bài", done = true)
            )
        ),
        Task(
            id = "2",
            title = "Ôn chương 3 - Cấu trúc dữ liệu",
            note = "Tập trung phần cây nhị phân và bảng băm.",
            category = "study",
            date = "2026-10-20",
            time = "14:00",
            priority = "medium",
            remind = true,
            done = false,
            subtasks = listOf(
                Subtask(id = 0, title = "Đọc lại slide bài giảng", done = true),
                Subtask(id = 1, title = "Làm 10 bài tập cuối chương", done = false)
            )
        ),
        Task(
            id = "3",
            title = "Đi chợ mua đồ cho tuần",
            note = "Mua thực phẩm tươi cho 1 tuần",
            category = "life",
            date = "2026-10-20",
            time = "17:30",
            priority = "medium",
            remind = true,
            done = false,
            subtasks = emptyList()
        ),
        Task(
            id = "4",
            title = "Tập thể dục 30 phút",
            note = "Chạy bộ công viên",
            category = "life",
            date = "2026-10-20",
            time = "06:30",
            priority = "medium",
            remind = true,
            done = true,
            subtasks = emptyList()
        ),
        Task(
            id = "5",
            title = "Gọi điện về nhà",
            note = "Hỏi thăm sức khỏe bố mẹ",
            category = "life",
            date = "2026-10-21",
            time = "21:00",
            priority = "low",
            remind = true,
            done = false,
            subtasks = emptyList()
        ),
        Task(
            id = "6",
            title = "Họp nhóm đồ án",
            note = "Phân công công việc tuần mới",
            category = "study",
            date = "2026-10-22",
            time = "20:00",
            priority = "high",
            remind = true,
            done = false,
            subtasks = emptyList()
        )
    )

    private val _tasks = MutableStateFlow<List<Task>>(initialTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    suspend fun fetchTasks(): Result<List<Task>> {
        return try {
            val response = RetrofitClient.apiService.getTasks()
            if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                _tasks.value = response.body()!!
                Result.success(response.body()!!)
            } else {
                // Return current local state
                Result.success(_tasks.value)
            }
        } catch (e: Exception) {
            // Fallback to local memory list if offline/API fails
            Result.success(_tasks.value)
        }
    }

    suspend fun toggleTaskDone(taskId: String): Result<Task> {
        val current = _tasks.value.find { it.id == taskId } ?: return Result.failure(Exception("Task not found"))
        val updated = current.copy(done = !current.done)
        _tasks.value = _tasks.value.map { if (it.id == taskId) updated else it }

        // Try syncing with API
        try {
            RetrofitClient.apiService.updateTask(taskId, mapOf("done" to updated.done))
        } catch (_: Exception) { }

        return Result.success(updated)
    }

    suspend fun addTask(task: Task): Result<Task> {
        val newTask = if (task.id.isEmpty()) task.copy(id = UUID.randomUUID().toString()) else task
        _tasks.value = listOf(newTask) + _tasks.value

        try {
            val response = RetrofitClient.apiService.createTask(newTask)
            if (response.isSuccessful && response.body() != null) {
                val created = response.body()!!
                _tasks.value = _tasks.value.map { if (it.id == newTask.id) created else it }
                return Result.success(created)
            }
        } catch (_: Exception) { }

        return Result.success(newTask)
    }

    suspend fun deleteTask(taskId: String): Result<Unit> {
        _tasks.value = _tasks.value.filterNot { it.id == taskId }
        try {
            RetrofitClient.apiService.deleteTask(taskId)
        } catch (_: Exception) { }
        return Result.success(Unit)
    }

    suspend fun updateSubtaskDone(taskId: String, subtaskId: Int, done: Boolean): Result<Task> {
        val task = _tasks.value.find { it.id == taskId } ?: return Result.failure(Exception("Task not found"))
        val updatedSubtasks = task.subtasks.map {
            if (it.id == subtaskId) it.copy(done = done) else it
        }
        val updatedTask = task.copy(subtasks = updatedSubtasks)
        _tasks.value = _tasks.value.map { if (it.id == taskId) updatedTask else it }
        return Result.success(updatedTask)
    }

    suspend fun addSubtask(taskId: String, title: String): Result<Task> {
        val task = _tasks.value.find { it.id == taskId } ?: return Result.failure(Exception("Task not found"))
        val newSubtaskId = (task.subtasks.maxOfOrNull { it.id } ?: -1) + 1
        val newSubtask = Subtask(id = newSubtaskId, title = title, done = false)
        val updatedTask = task.copy(subtasks = task.subtasks + newSubtask)
        _tasks.value = _tasks.value.map { if (it.id == taskId) updatedTask else it }
        return Result.success(updatedTask)
    }

    suspend fun deleteSubtask(taskId: String, subtaskId: Int): Result<Task> {
        val task = _tasks.value.find { it.id == taskId } ?: return Result.failure(Exception("Task not found"))
        val updatedSubtasks = task.subtasks.filterNot { it.id == subtaskId }
        val updatedTask = task.copy(subtasks = updatedSubtasks)
        _tasks.value = _tasks.value.map { if (it.id == taskId) updatedTask else it }
        return Result.success(updatedTask)
    }

    fun resetToMockData() {
        _tasks.value = initialTasks
    }
}
