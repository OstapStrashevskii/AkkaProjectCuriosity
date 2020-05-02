package akkaprojcuriosity.datasource

import com.redis._
import com.redis.RedisClient

object DataStorageModule extends App {


  val redisClient = new RedisClient("localhost", 6379)
  redisClient.set("key", "some value")
  val res = redisClient.get("key")
  println("res="+res)


//  redisClient.set("hhh", 5)
//  val hhh = redisClient.get("hhh")
//  val n: Int = hhh match {
//    case Some(n) => println(n)
//      n.toInt
//    case None => println("none")
//      1111111
//  }
//
//  redisClient.set("hhh", n + 15)
//
//  val hhhhh = redisClient.get("hhh")
//  println(hhhhh)



  val r = new RequestInfoEntry(new RedisDataSource() with Utils) with Utils

  r.setRejectedNum()
  println(r.getRjectedNum)
  r.incrementRjectedNum
  r.incrementRjectedNum
  r.incrementRjectedNum
  println(r.getRjectedNum)


}
