package com.mimu

/**
 * Created by mm on 13/11/2014.
 */

import java.util.concurrent.atomic.AtomicLong

import org.vertx.scala.core.http.{HttpClientRequest, HttpClient}
import org.vertx.scala.platform.Verticle
import org.vertx.scala.core.http._
import org.vertx.scala.core.buffer._


class PollerVerticle extends Verticle {
  val VERSION: String = "0.0.1"
  val REPEAT_PARAMETER: String = "r"
  private val receivedCounter: AtomicLong = new AtomicLong
  private var pollMillis: Long = 0L
  private val MAX_POLL: Int = 10

  override def start(): Unit = {

    val server: HttpServer = vertx.createHttpServer

    server.requestHandler{ req:HttpServerRequest => {
      receivedCounter.set(0L)
      pollMillis = System.currentTimeMillis
      val maxPoll: Int = HttpUtil.getIntParam(req.params(), REPEAT_PARAMETER, MAX_POLL)
      val path: String = req.path
      writeConfirmationResponse(req, req.response, maxPoll, path)
      doPolling(maxPoll)
    } }

    server.listen(9191)

  }

  /**
   * writes confirmation response
   */
  private def writeConfirmationResponse(req: HttpServerRequest, resp: HttpServerResponse, maxPoll: Int, path: String):Unit = {
    resp.setChunked(true)
    resp.setStatusCode(200)
    resp.putHeader("Content-Type", "text/html")
    resp.write("<html><body>")
    resp.write("<h2>headers:</h2>")
    HttpUtil.sendMultiMap(resp, req.headers)
    resp.write("<h2>params:</h2>")
    HttpUtil.sendMultiMap(resp, req.params)
    resp.write("<h2>path:</h2>")
    resp.write(path)
    resp.write("<h2>maxPoll:</h2>")
    resp.write("" + maxPoll)
    resp.write("<h2>version:</h2>")
    resp.write(VERSION)
    resp.write("</body></html>")
    resp.end
  }

  /**
   * do polling
   *
   * polls host/port a given number of times
   *
   * @param numPoll number of times to poll
   */
  private def doPolling(numPoll: Int) {
    val client: HttpClient = vertx.createHttpClient.setHost("localhost").setPort(9111)

    for (i <- 0 until numPoll)
    {
      doSinglePoll(client, numPoll)
    }
  }

  /**
   * do single poll
   */
  private def doSinglePoll(client: HttpClient, maxPoll: Int) {
    val request: HttpClientRequest = client.post("/actionEndpoint?wsdl", {resp: HttpClientResponse => {
      resp.bodyHandler({buf:Buffer => {
        if (receivedCounter.incrementAndGet() == maxPoll) {
          val millis: Long = System.currentTimeMillis() - pollMillis;
          println("received $maxPoll in $millis   buffer = $buf", maxPoll, millis, buf);
        }
      }})
    }})
    request.setChunked(true)
    request.write("text")
    request.end
  }
}
