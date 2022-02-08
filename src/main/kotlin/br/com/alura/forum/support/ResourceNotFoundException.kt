package br.com.alura.forum.support

import kotlin.reflect.KClass

class ResourceNotFoundException(attribute: String, klass: KClass<*>) : Exception("${klass.simpleName} with $attribute is not found")
