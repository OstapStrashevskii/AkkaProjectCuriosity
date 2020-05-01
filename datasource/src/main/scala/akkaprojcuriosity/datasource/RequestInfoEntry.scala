package akkaprojcuriosity.datasource

class RequestInfoEntry(redisDataSource: RedisDataSource) {
  self: Utils =>

  def setRejectedNum(): Boolean = {
    redisDataSource.redisClient.set(RejectedRequestNum, "0")
  }

  def setSuccessfullNum(): Boolean = {
    redisDataSource.redisClient.set(SuccessfulRequestsNum, "0")
  }

  def getRjectedNum: Option[String] = {
    redisDataSource.redisClient.get(RejectedRequestNum)
  }

  def getSuccessfullNum: Option[String] = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum)
  }

  def incrementRjectedNum: Boolean = {
    redisDataSource.redisClient.get(RejectedRequestNum) match {
      case Some(num) =>
        println("num=" + num)
        println("num.toInt + " + (num.toInt + 1))
        redisDataSource.redisClient.set(RejectedRequestNum, num.toInt + 1)
      case None => throw  new Exception("redis can not get SuccessfulRequestsNum")
    }
  }

  def incrementSuccessfullNum: Boolean = {
    redisDataSource.redisClient.get(SuccessfulRequestsNum) match {
      case Some(num) => redisDataSource.redisClient.set(SuccessfulRequestsNum, num.toInt + 1)
      case None => throw  new Exception("redis can not get RejectedRequestsNum")
    }
  }
}
