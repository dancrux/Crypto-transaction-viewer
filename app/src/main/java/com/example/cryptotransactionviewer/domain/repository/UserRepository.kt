package com.example.cryptotransactionviewer.domain.repository

import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun login(username: String, password: String): Flow<Resource<User>>
    suspend fun logout(): Flow<Resource<Boolean>>
    suspend fun isLoggedIn(): Flow<Boolean>
    suspend fun getUser(): Flow<User?>
}