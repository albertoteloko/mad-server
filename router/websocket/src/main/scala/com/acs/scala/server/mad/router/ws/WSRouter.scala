package com.acs.scala.server.mad.router.ws

import com.acs.scala.server.mad.LogSupport
import com.acs.scala.server.mad.router.ws.handler.{DefaultHandler, WSHandler}
import com.acs.scala.server.mad.router.ws.model.{WSRequest, WSResponse, WSResponseBuilder}
import com.acs.scala.server.mad.utils.{LogLevel, StopWatch}

private[router] case class WSRoute(uri: String, subprotocol: String)

case class WSRequestContext
(
  request: WSRequest,
  response: WSResponseBuilder,
  route: Option[WSRoute] = None
)

trait WSRouter extends LogSupport {

  protected var wsRoutes: Map[WSRoute, WSHandler] = Map()
  protected val defaultHandler: WSHandler = new DefaultHandler()

  private[ws] def route(route: WSRoute)(handler: WSHandler): Unit = wsRoutes = wsRoutes + (route -> handler)

  def process(request: WSRequest): WSResponse = {
    implicit val context: WSRequestContext = WSRequestContext(request, WSResponseBuilder(request))
    log.trace("WS Request {} {}", Array(request.uri, request.subprotocol): _*)
    val stopWatch = new StopWatch().start()
    try {
      wsRoutes.getOrElse(WSRoute(request.uri.toString, request.subprotocol), defaultHandler).handle
    } catch {
      case e: Exception =>
        log.error("Error during request", e)
        throw e
    } finally {
      stopWatch.printElapseTime("Request " + request.uri + " " + request.subprotocol, log, LogLevel.DEBUG)
    }
  }
}
