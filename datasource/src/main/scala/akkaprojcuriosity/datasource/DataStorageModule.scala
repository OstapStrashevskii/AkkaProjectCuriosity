package akkaprojcuriosity.datasource

import com.redis._
import com.redis.RedisClient
import com.typesafe.scalalogging.Logger

object DataStorageModule extends App {

  val logger = Logger("logger")//todo
  logger.debug("ffffff")
  logger.info("ffffff")
  logger.error("ffffff")
  logger.warn("ffffff")

//  val redisClient = new RedisClient("localhost", 6379)
//  redisClient.set("key", "some value")
//  val res = redisClient.get("key")
//  println("res="+res)
//  val r = new RequestInfoEntry(new RedisDataSource() with Utils) with Utils
//  r.setRejectedNum(0)
//  println(r.getRjectedNum)
//  r.incrementRjectedNum
//  r.incrementRjectedNum
//  r.incrementRjectedNum
//  println(r.getRjectedNum)
}
