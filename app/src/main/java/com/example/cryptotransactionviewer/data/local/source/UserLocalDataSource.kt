package com.example.cryptotransactionviewer.data.local.source

import com.example.cryptotransactionviewer.data.local.dao.UserDao
import com.example.cryptotransactionviewer.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private  val userDao: UserDao
) {
    fun getUser(): Flow<UserEntity?> {
        return userDao.getUser()
    }

    suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun clearUsers() {
        userDao.clearUsers()
    }
}