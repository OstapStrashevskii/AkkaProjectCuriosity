package akkaprojcuriosity.datasource

class RequestInfoEntry(redisDataSource: RedisDataSource) {
  self: Utils =>

  def getRjectedNum: Option[String] = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum)
  }

  def getSuccessfullNum: Option[String] = {
    redisDataSource.redisClient.get(RejectedRequestNum)
  }

  def incrementRjectedNum: Boolean = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum) match {
      case Some(num) => redisDataSource.redisClient.set(SuccessfulRequestsNum, num)
      case None => throw  new Exception("redis can not get SuccessfulRequestsNum")
    }
  }

  def incrementSuccessfullNum: Boolean = {
    redisDataSource.redisClient.get(RejectedRequestNum) match {
      case Some(num) => redisDataSource.redisClient.set(RejectedRequestNum, num)
      case None => throw  new Exception("redis can not get RejectedRequestsNum")
    }
  }
}
