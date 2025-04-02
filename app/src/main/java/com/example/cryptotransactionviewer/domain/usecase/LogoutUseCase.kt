package com.example.cryptotransactionviewer.domain.usecase

import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private  val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return userRepository.logout()
    }
}