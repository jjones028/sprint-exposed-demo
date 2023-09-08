package com.example.springexposeddemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringExposedDemoApplication

fun main(args: Array<String>) {
    runApplication<SpringExposedDemoApplication>(*args)
}
