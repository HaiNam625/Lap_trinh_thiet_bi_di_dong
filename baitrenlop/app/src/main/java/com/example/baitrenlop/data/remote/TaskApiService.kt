package com.example.baitrenlop.data.remote

import com.example.baitrenlop.data.model.Task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {

    @GET("mock/teach/tasks")
    suspend fun getTasks(): Response<List<Task>>

    @GET("mock/teach/tasks/{id}")
    suspend fun getTaskDetail(@Path("id") id: String): Response<Task>

    @POST("mock/teach/tasks")
    @Headers("Content-Type: application/json")
    suspend fun createTask(@Body task: Task): Response<Task>

    @PATCH("mock/teach/tasks/{id}")
    @Headers("Content-Type: application/json")
    suspend fun updateTask(@Path("id") id: String, @Body task: Map<String, Any>): Response<Task>

    @DELETE("mock/teach/tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<Unit>
}
