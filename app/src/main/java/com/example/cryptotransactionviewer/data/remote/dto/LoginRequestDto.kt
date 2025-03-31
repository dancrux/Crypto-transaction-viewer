package com.example.cryptotransactionviewer.data.remote.dto

import com.example.cryptotransactionviewer.domain.model.User
import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName( "username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName( "token") val token: String
)

fun UserDto.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        token = token
    )
}
