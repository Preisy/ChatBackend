package ru.preis.api.controller.users.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/users")
class UsersResources {
    @Serializable
    @Resource("/{id}")
    class Id(val parent: UsersResources = UsersResources(), val id: Int)
}