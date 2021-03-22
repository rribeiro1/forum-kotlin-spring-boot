package br.com.alura.forum.support

import kotlin.reflect.KClass

class ResourceNotFoundException(id: Long, klass: KClass<*>) : Exception("${klass.simpleName} with id($id) is not found")
