package com.acs.scala.server.mad.router.constant

import enumeratum._

sealed trait RequestMethod extends EnumEntry

object RequestMethod extends Enum[RequestMethod] {
  val values = findValues

  case object OPTIONS extends RequestMethod

  case object GET extends RequestMethod

  case object HEAD extends RequestMethod

  case object POST extends RequestMethod

  case object PUT extends RequestMethod

  case object PATCH extends RequestMethod

  case object DELETE extends RequestMethod

  case object TRACE extends RequestMethod

  case object CONNECT extends RequestMethod

}

