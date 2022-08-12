package com.qecodingcamp.applepie.controller.hello

import com.qecodingcamp.applepie.service.hello.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    private val helloService: HelloService
) {

    @GetMapping("/hello")
    //Alternative way: @RequestMapping(method=[RequestMethod.GET], path = ["/hello"])
    fun hello(
        @RequestParam("personName") name: String
    ): String {
        return helloService.sayHello(name)
    }
}
