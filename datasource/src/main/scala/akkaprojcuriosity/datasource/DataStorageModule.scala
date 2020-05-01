package akkaprojcuriosity.datasource

import com.redis._
import com.redis.RedisClient

object DataStorageModule extends App {


  val redisClient = new RedisClient("localhost", 6379)
//  redisClient.set("key", "some value")
//  val res = redisClient.get("key")
//  println("res="+res)



}
