package com.dmm.bootcamp.yatter2024.domain.service

interface RegisterService {
    suspend fun execute(
        username: String,
        password: String,
    )
}