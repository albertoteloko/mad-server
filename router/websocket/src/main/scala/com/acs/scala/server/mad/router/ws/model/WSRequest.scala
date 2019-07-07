package com.acs.scala.server.mad.router.ws.model

import java.net.URI

sealed trait WSRequest {
  val id: String
  val remoteAddress: String
  val uri: URI
  val subprotocol: String
}

case class WSRequestConnect
(
  id: String,
  remoteAddress: String,
  uri: URI,
  subprotocol: String
) extends WSRequest

case class WSRequestDisconnect
(
  id: String,
  remoteAddress: String,
  uri: URI,
  subprotocol: String
) extends WSRequest

case class WSRequestBinary
(
  id: String,
  remoteAddress: String,
  uri: URI,
  subprotocol: String,
  bytes: Array[Byte]
) extends WSRequest

case class WSRequestText
(
  id: String,
  remoteAddress: String,
  uri: URI,
  subprotocol: String,
  text: String
) extends WSRequest