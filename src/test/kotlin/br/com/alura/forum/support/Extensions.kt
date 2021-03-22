package br.com.alura.forum.support

import io.restassured.response.ValidatableResponse
import kotlin.reflect.KClass
import org.springframework.http.HttpStatus

fun ValidatableResponse.statusCode(status: HttpStatus): ValidatableResponse = statusCode(status.value())

fun <T : Any> ValidatableResponse.extract(kClass: KClass<T>): T = extract().`as`(kClass.java)

/**
 * Extract the content from a response based on Spring's Page<T> type
 */
fun <T : Any> ValidatableResponse.extractContent(kClass: KClass<T>): MutableList<T> =
    extract().jsonPath().getList("content", kClass.java)
