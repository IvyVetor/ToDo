package io.vertx.blog.first

import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

fun main(args: Array<String>) {

    println("Hello World!")

    val vertx = Vertx.vertx()
    val httpServer = vertx.createHttpServer()

    val router = Router.router(vertx)

    var mytodo = arrayListOf("sample item")

    router.get("/")
            .handler({
                routingContext ->
                val response = routingContext.response()
                response.putHeader("content-type", "text/plain")
                        .setChunked(true)
                        .write("Hi youtube!\n")
                        .end("Ended")
            })

    router.get("/list")
            .handler({
                routingContext ->
                val response = routingContext.response()
                response.putHeader("content-type", "text/plain")
                        .setChunked(true)
                        .write("Here's your list! " + mytodo + "")
                        .end("Ended")
            })

    router.get("/add/:thing")
            .handler({
                routingContext ->

                val request = routingContext.request()
                val thing = request.getParam("thing")
                mytodo.add(thing)

                val response = routingContext.response()
                response.putHeader("content-type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(ResponseObj("Hello, you have added " + thing + " to your todo list: " + mytodo)))
                        .end()
            })


    router.get("/remove/:index")
            .handler({
                routingContext ->

                val request = routingContext.request()
                val index= request.getParam("index").toInt()
                mytodo.removeAt(index)

                val response = routingContext.response()
                response.putHeader("content-type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(DifferentResponseObj("Hello, you have removed item at index " + index + " from your todo list: " + mytodo)))
                        .end()
            })

    httpServer
            .requestHandler(router::accept)
            .listen(System.getenv("PORT").toInt())
}


data class ResponseObj(var thing:String = "")
data class DifferentResponseObj(var index:String = "")