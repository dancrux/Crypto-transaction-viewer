package com.example.cryptotransactionviewer.data.remote.api

import com.example.cryptotransactionviewer.data.remote.dto.LoginRequestDto
import com.example.cryptotransactionviewer.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequestDto): UserDto
}