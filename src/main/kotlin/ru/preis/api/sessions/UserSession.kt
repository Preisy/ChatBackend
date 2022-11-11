package ru.preis.api.sessions

import io.ktor.server.auth.*

// todo не будет работать если один пользователь зайдет дважды
//  надо генерировать sessionId самостоятельно
data class UserSession(
//    val sessionId: Int,
    val userId: UInt
) : Principal