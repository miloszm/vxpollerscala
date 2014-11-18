package com.mimu

import org.vertx.scala.core.http._
import org.vertx.scala.core.MultiMap

import scala.util.Try

/**
 * Created by mm on 13/11/2014.
 */
object HttpUtil {

  def sendMultiMap(resp: HttpServerResponse, headers: MultiMap):Unit = {
    for (e <- headers; s <- e._2) {
      resp.write("<p>" + s + "</p>");
    }
  }

  def getIntParam(params: MultiMap, paramName: String, defaultValue: Int) : Int = {
    val s:scala.collection.mutable.Set[String] = params.getOrElse(paramName, scala.collection.mutable.Set[String]())
    val e:Option[String] = s.find( _ => true)
    Try(e.getOrElse("").toInt).getOrElse(defaultValue)
  }

}
