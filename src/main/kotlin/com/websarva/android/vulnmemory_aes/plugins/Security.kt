package com.websarva.android.vulnmemory_aes.plugins

import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.sessions.*

fun Application.configureSecurity(env: ApplicationEnvironment) {
    // Sessionの作成
    install(Sessions){
        cookie<MySession>("SESSION")
    }
    // 認証情報の定義
    install(Authentication){
        basic {
            validate { credentials ->
                if (credentials.name == env.config.property("basic.username").getString() && credentials.password == env.config.property("basic.password").getString())
                    MySession(credentials.name)
                else
                    null
            }
        }
    }
}

data class MySession(val name: String): Principal