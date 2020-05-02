package akkaprojcuriosity.server

import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}

object ApplicationComponents {

  lazy val redisDataSource: RedisDataSource = new RedisDataSource() with Utils
  lazy val requestInfoEntry: RequestInfoEntry = new RequestInfoEntry(redisDataSource) with Utils
  lazy val handlers: Handlers = new Handlers(requestInfoEntry)

}
