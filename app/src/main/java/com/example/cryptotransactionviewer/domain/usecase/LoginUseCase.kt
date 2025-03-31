package com.example.cryptotransactionviewer.domain.usecase

import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.User
import com.example.cryptotransactionviewer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String):Flow<Resource<User>>{
        return userRepository.login(username, password)
    }
}