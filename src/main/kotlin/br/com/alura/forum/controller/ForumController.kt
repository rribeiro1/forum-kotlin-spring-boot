package br.com.alura.forum.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ForumController {
    @GetMapping("/")
    fun hello(): String = "Hello World"
}