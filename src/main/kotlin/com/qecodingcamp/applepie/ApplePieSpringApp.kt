package com.qecodingcamp.applepie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.qecodingcamp.applepie.config")
open class ApplePieSpringApp

fun main(args: Array<String>) {
	runApplication<ApplePieSpringApp>(*args)
}
