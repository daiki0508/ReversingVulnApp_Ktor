package com.websarva.android.vulnmemory_aes.plugins

import com.websarva.android.vulnmemory_aes.route.authBasic
import com.websarva.android.vulnmemory_aes.route.vulnMemoryAESRoute
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    install(Locations)

    routing {
        authBasic()
        vulnMemoryAESRoute()
    }
}
