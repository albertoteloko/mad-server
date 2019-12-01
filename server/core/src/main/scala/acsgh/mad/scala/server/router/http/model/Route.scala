package acsgh.mad.scala.server.router.http.model

import acsgh.mad.scala.core.URLSupport
import acsgh.mad.scala.core.http.model.{HttpRequest, RequestMethod}

trait Route[T] extends URLSupport {

  val uri: String
  val methods: Set[RequestMethod]
  val action: T

  private[http] def canApply(request: HttpRequest): Boolean = validMethod(request) && matchUrl(uri, request.uri)

  private def validMethod(request: HttpRequest): Boolean = methods.isEmpty || methods.contains(request.method)

  protected def validUrl(uri: String): Unit = {
    if (!uri.startsWith("/")) {
      throw new IllegalArgumentException("The route must start with /")
    }

    if ((uri != "/") && uri.endsWith("/")) {
      throw new IllegalArgumentException("The route cannot end with /")
    }
  }
}

private[http] case class HttpRoute[T](uri: String, methods: Set[RequestMethod], action: T) extends Route[T] {
  validUrl(uri)
}
