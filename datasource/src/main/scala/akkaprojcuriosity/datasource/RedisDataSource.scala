package akkaprojcuriosity.datasource

import com.redis.RedisClient

class RedisDataSource {
  self: Utils =>
  val redisClient = new RedisClient("localhost", 6379)
}
