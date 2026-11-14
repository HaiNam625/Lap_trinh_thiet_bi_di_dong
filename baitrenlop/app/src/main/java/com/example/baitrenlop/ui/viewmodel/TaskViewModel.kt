package com.example.baitrenlop.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baitrenlop.data.model.Task
import com.example.baitrenlop.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.tasks

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("Tất cả")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.fetchTasks()
            _isLoading.value = false
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectTask(task: Task?) {
        _selectedTask.value = task
    }

    fun selectTaskById(taskId: String) {
        _selectedTask.value = tasks.value.find { it.id == taskId }
    }

    fun toggleTaskDone(taskId: String) {
        viewModelScope.launch {
            repository.toggleTaskDone(taskId)
            if (_selectedTask.value?.id == taskId) {
                _selectedTask.value = repository.tasks.value.find { it.id == taskId }
            }
        }
    }

    fun addTask(task: Task, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.addTask(task)
            onComplete()
        }
    }

    fun deleteTask(taskId: String, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
            if (_selectedTask.value?.id == taskId) {
                _selectedTask.value = null
            }
            onComplete()
        }
    }

    fun addSubtask(taskId: String, title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.addSubtask(taskId, title.trim())
            if (_selectedTask.value?.id == taskId) {
                _selectedTask.value = repository.tasks.value.find { it.id == taskId }
            }
        }
    }

    fun toggleSubtaskDone(taskId: String, subtaskId: Int, done: Boolean) {
        viewModelScope.launch {
            repository.updateSubtaskDone(taskId, subtaskId, done)
            if (_selectedTask.value?.id == taskId) {
                _selectedTask.value = repository.tasks.value.find { it.id == taskId }
            }
        }
    }

    fun deleteSubtask(taskId: String, subtaskId: Int) {
        viewModelScope.launch {
            repository.deleteSubtask(taskId, subtaskId)
            if (_selectedTask.value?.id == taskId) {
                _selectedTask.value = repository.tasks.value.find { it.id == taskId }
            }
        }
    }

    // Filtered lists for Task List Screen
    val filteredPendingTasks: StateFlow<List<Task>> = combine(
        tasks,
        _searchQuery,
        _selectedCategory
    ) { taskList, query, category ->
        taskList.filter { task ->
            !task.done &&
                    (query.isEmpty() || task.title.contains(query, ignoreCase = true) || task.note.contains(query, ignoreCase = true)) &&
                    (category == "Tất cả" || task.categoryDisplayName.equals(category, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredCompletedTasks: StateFlow<List<Task>> = combine(
        tasks,
        _searchQuery,
        _selectedCategory
    ) { taskList, query, category ->
        taskList.filter { task ->
            task.done &&
                    (query.isEmpty() || task.title.contains(query, ignoreCase = true) || task.note.contains(query, ignoreCase = true)) &&
                    (category == "Tất cả" || task.categoryDisplayName.equals(category, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalPendingCount: StateFlow<Int> = tasks.combine(_searchQuery) { taskList, _ ->
        taskList.count { !it.done }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
}
