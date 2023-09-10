package com.example.springexposeddemo

import com.example.springexposeddemo.dialect.IRISDialect
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.jetbrains.exposed.sql.Database
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@SpringBootApplication
@EnableTransactionManagement
class SpringExposedDemoApplication {
    @Bean
    fun transactionManager(dataSource: DataSource) = SpringTransactionManager(dataSource)
}

fun main(args: Array<String>) {
    Database.registerDialect(IRISDialect.dialectName) { IRISDialect() }

    Database.registerJdbcDriver(
        "jdbc:IRIS",
        "com.intersystems.jdbc.IRISDriver",
        "intersystems iris"
    )
    runApplication<SpringExposedDemoApplication>(*args)
}
