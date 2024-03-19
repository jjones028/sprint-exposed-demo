package com.example.springexposeddemo.controller

import com.example.springexposeddemo.repository.SubjectRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subjects")
class SubjectController(val repository: SubjectRepository) {
    @GetMapping
    fun findAll() = repository.queryAll()
}
