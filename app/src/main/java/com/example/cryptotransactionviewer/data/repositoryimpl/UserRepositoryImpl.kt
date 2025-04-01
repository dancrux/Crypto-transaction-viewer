package com.example.cryptotransactionviewer.data.repositoryimpl

import com.example.cryptotransactionviewer.data.local.entity.toUser
import com.example.cryptotransactionviewer.data.local.entity.toUserEntity
import com.example.cryptotransactionviewer.data.local.source.UsersLocalDataSource
import com.example.cryptotransactionviewer.data.remote.dto.toUser
import com.example.cryptotransactionviewer.data.remote.source.UserRemoteDataSource
import com.example.cryptotransactionviewer.domain.model.Resource
import com.example.cryptotransactionviewer.domain.model.User
import com.example.cryptotransactionviewer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UsersLocalDataSource
)  : UserRepository{
    override suspend fun login(username: String, password: String): Flow<Resource<User>> = flow {
       emit(Resource.Loading())

        try {
            // Perform login request
            val loginResponse = remoteDataSource.login(username, password)
            val user = loginResponse.toUser()

            // Cache user data
            localDataSource.saveUser(user.toUserEntity())
            emit(Resource.Success(user))
        }catch (e: HttpException) {
            emit(Resource.Error("Login failed: ${e.localizedMessage}"))
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error: ${e.localizedMessage}"))
        }
    }

    override suspend fun logout(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        try {
//            clear local user data
            localDataSource.clearUsers()
            emit(Resource.Success(true))
        }catch (e: Exception) {
            emit(Resource.Error("Logout failed: ${e.localizedMessage}"))
        }
    }

    override suspend fun isLoggedIn(): Flow<Boolean> {
       return localDataSource.getUser().map { it != null }
    }

    override suspend fun getUser(): Flow<User?> {
        return localDataSource.getUser().map { it?.toUser() }
    }
}