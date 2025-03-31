package com.example.cryptotransactionviewer.data.remote.dto

import com.google.gson.annotations.SerializedName


data class TezosBlockDto(
    @SerializedName("level") val level: Long,
    @SerializedName("hash") val hash: String,
    @SerializedName("timestamp") val timestamp: String
)

data class TezosOperationDto(
    @SerializedName("hash") val hash: String,
    @SerializedName("type") val type: String,
    @SerializedName("amount") val amount: Long?,
    @SerializedName("sender") val sender: TezosAddressDto?,
    @SerializedName("target") val target: TezosAddressDto?
)

data class TezosAddressDto(
    @SerializedName("address") val address: String
)