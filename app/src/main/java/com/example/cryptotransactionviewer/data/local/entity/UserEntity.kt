package com.example.cryptotransactionviewer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cryptotransactionviewer.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val token: String
)

fun UserEntity.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        token = token
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        username = username,
        email = email,
        token = token
    )
}
