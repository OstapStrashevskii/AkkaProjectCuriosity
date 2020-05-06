package akkaprojcuriosity.services

import akkaprojcuriosity.datasource.{ RequestInfoEntry }

class RequestInfoService(requestInfoEntry: RequestInfoEntry) {

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
