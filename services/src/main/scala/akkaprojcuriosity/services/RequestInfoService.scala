package akkaprojcuriosity.services

import akkaprojcuriosity.datasource.{RedisDataSource, RequestInfoEntry, Utils}

class RequestInfoService(requestInfoEntry: RequestInfoEntry) {
//  self: RedisDataSource with Utils =>

  def getRjectedNum: Option[String] = {
    requestInfoEntry.getRjectedNum
  }

  def getSuccessfullNum: Option[String] = {
    requestInfoEntry.getSuccessfullNum
  }

  def incrementRjectedNum: Boolean = {
    requestInfoEntry.incrementRjectedNum
  }

  def incrementSuccessfullNum: Boolean = {
    requestInfoEntry.incrementSuccessfullNum
  }
}
