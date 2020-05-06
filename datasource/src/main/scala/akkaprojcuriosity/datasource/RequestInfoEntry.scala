package akkaprojcuriosity.datasource

import scala.concurrent.Future

class RequestInfoEntry(redisDataSource: RedisDataSource) {
  self: Utils => //todo do not use cake pattern with over types of injections

  def setRejectedNum(num: Int): Boolean = {
    redisDataSource.redisClient.set(RejectedRequestNum, num)
  }

  def setSuccessfulNum(num: Int): Boolean = {
    redisDataSource.redisClient.set(SuccessfulRequestsNum, num)
  }

  def getRejectedNum: Option[String] = {
    redisDataSource.redisClient.get(RejectedRequestNum)
  }

  def getSuccessfulNum: Option[String] = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum)
  }

  def incrementRjectedNum: Boolean = {
    redisDataSource.redisClient.get(RejectedRequestNum) match {
      case Some(num) =>
        redisDataSource.redisClient.set(RejectedRequestNum, num.toInt + 1)
//        redisDataSource.redisClient.with//todo with future - non-blocking
      case None => throw  new Exception("redis can not get SuccessfulRequestsNum")
    }
  }

  def incrementSuccessfulNum: Boolean = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum) match {
      case Some(num) => redisDataSource.redisClient.set(SuccessfulRequestsNum, num.toInt + 1)
      case None => throw  new Exception("redis can not get RejectedRequestsNum")
    }
  }
}
