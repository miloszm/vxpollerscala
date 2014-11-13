package com.mimu

import org.junit.Test
import org.junit.Assert.assertEquals
import scala.collection.mutable.HashMap
import scala.collection.mutable.Set
import org.vertx.scala.core.MultiMap

/**
 * Created by mm on 13/11/2014.
 */
class HttpUtilTest {

  @Test
  def getIntParamTest_provided(): Unit = {

    val map:MultiMap = new HashMap[String, Set[String]] with scala.collection.mutable.MultiMap[String, String]

    map.put("r", scala.collection.mutable.Set("100"))

    val p:Int = HttpUtil.getIntParam(map, "r", 200)

    println(p)

    assertEquals(100, p)

  }

  @Test
  def getIntParamTest_missing(): Unit = {

    val map:MultiMap = new HashMap[String, Set[String]] with scala.collection.mutable.MultiMap[String, String]

    val p:Int = HttpUtil.getIntParam(map, "r", 200)

    println(p)

    assertEquals(200, p)

  }

  @Test
  def getIntParamTest_nonNumeric(): Unit = {

    val map:MultiMap = new HashMap[String, Set[String]] with scala.collection.mutable.MultiMap[String, String]

    map.put("r", scala.collection.mutable.Set("abc"))

    val p:Int = HttpUtil.getIntParam(map, "r", 200)

    println(p)

    assertEquals(200, p)

  }


}
