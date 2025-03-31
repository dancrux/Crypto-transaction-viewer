package com.example.cryptotransactionviewer.data.remote.source

import com.example.cryptotransactionviewer.data.remote.api.AuthApi
import com.example.cryptotransactionviewer.data.remote.dto.LoginRequestDto
import com.example.cryptotransactionviewer.data.remote.dto.UserDto
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    suspend fun login(username: String, password: String): UserDto {
        return authApi.login(LoginRequestDto(username, password))
    }
}