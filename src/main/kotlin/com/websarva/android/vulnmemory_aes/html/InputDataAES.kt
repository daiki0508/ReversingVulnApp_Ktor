package com.websarva.android.vulnmemory_aes.html

import kotlinx.html.*

fun inputDataAES(
    encodeSrc: String = "",
    encodeKey: String = "",
    encodeIV: String = "",
    error: String = ""): HTML.() -> Unit = {
        head {
            link(rel = "stylesheet", href = "https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css")
        }
    body {
        div(classes = "container"){
            if (error.isNotBlank()){
                p(classes = "text-danger") {+error}
            }
            postForm(action = "/vulnMemoryAES/solve", encType = FormEncType.applicationXWwwFormUrlEncoded){
                div { +"encodeSrc" }
                div { textInput(name = "encodeSrc", classes = "form-control") {value = encodeSrc} }
                div { +"encodeKey" }
                div { textInput(name = "encodeKey", classes = "form-control") {value = encodeKey} }
                div { +"encodeIV" }
                div { textInput(name = "encodeIV", classes = "form-control") {value = encodeIV} }
                br
                div { submitInput(classes = "btn btn-outline-primary") {value = "Solve"} }
            }
        }
    }
}