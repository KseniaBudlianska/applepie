package com.qecodingcamp.applepie.service.hello

import org.springframework.stereotype.Service

@Service
class HelloService {

    fun sayHello(name: String): String {
        return "Hello, $name"
    }
}
