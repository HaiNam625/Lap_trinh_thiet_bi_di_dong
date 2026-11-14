package com.example.baitrenlop.data.model

import com.google.gson.annotations.SerializedName

data class Subtask(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("done") val done: Boolean = false
)

data class Task(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("note") val note: String = "",
    @SerializedName("category") val category: String = "study", // "study", "life", etc.
    @SerializedName("date") val date: String = "2026-10-20",
    @SerializedName("time") val time: String = "09:00",
    @SerializedName("priority") val priority: String = "medium", // "high", "medium", "low"
    @SerializedName("remind") val remind: Boolean = true,
    @SerializedName("done") val done: Boolean = false,
    @SerializedName("subtasks") val subtasks: List<Subtask> = emptyList()
) {
    val categoryDisplayName: String
        get() = when (category.lowercase()) {
            "study", "học tập" -> "Học tập"
            "life", "cuộc sống" -> "Cuộc sống"
            else -> category
        }

    val priorityDisplayName: String
        get() = when (priority.lowercase()) {
            "high", "gấp", "cao" -> "Ưu tiên cao"
            "medium", "vừa" -> "Ưu tiên vừa"
            "low", "thấp" -> "Ưu tiên thấp"
            else -> priority
        }
}

data class UserProfile(
    val uid: String = "user_123",
    val name: String = "Minh Anh",
    val email: String = "minhanh@example.com",
    val photoUrl: String? = null,
    val isGoogleLinked: Boolean = true,
    val isAnonymous: Boolean = false
)
