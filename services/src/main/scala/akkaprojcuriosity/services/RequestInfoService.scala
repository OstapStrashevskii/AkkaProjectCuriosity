package akkaprojcuriosity.services

import akkaprojcuriosity.datasource.{ RequestInfoEntry }

class RequestInfoService(requestInfoEntry: RequestInfoEntry) {

  def getRejectedNum: Option[String] = {
    requestInfoEntry.getRejectedNum
  }

  def getSuccessfullNum: Option[String] = {
    requestInfoEntry.getSuccessfulNum
  }

  def incrementRjectedNum: Boolean = {
    requestInfoEntry.incrementRjectedNum
  }

  def incrementSuccessfullNum: Boolean = {
    requestInfoEntry.incrementSuccessfulNum
  }
}
