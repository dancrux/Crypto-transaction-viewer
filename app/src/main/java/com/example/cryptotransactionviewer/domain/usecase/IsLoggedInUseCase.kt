package com.example.cryptotransactionviewer.domain.usecase

import com.example.cryptotransactionviewer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Flow<Boolean>{
        return userRepository.isLoggedIn()
    }
}