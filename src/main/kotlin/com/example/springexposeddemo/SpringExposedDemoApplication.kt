package com.example.springexposeddemo

import com.example.springexposeddemo.dialect.IRISDialect
import com.example.springexposeddemo.entity.Subjects
import org.jetbrains.exposed.spring.autoconfigure.ExposedAutoConfiguration
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
@ImportAutoConfiguration(
    value = [ExposedAutoConfiguration::class],
    exclude = [DataSourceTransactionManagerAutoConfiguration::class]
)
class SpringExposedDemoApplication

fun main(args: Array<String>) {
    Database.registerDialect(IRISDialect.dialectName) { IRISDialect() }

    Database.registerJdbcDriver(
        "jdbc:IRIS",
        "com.intersystems.jdbc.IRISDriver",
        "intersystems iris"
    )
    runApplication<SpringExposedDemoApplication>(*args)
}
@Component
class MyCommandLineRunner: CommandLineRunner {
    override fun run(vararg args: String?) {
        transaction {
            SchemaUtils.create(Subjects)
        }

    }

}
