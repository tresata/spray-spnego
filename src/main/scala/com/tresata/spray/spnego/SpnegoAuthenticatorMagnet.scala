package com.tresata.spray.spnego

import scala.concurrent.ExecutionContext

import spray.routing.Directive1
import spray.routing.directives.AuthMagnet
import spray.routing.directives.BasicDirectives.{ extract, provide }
import spray.routing.directives.RouteDirectives.reject
import spray.routing.directives.FutureDirectives.onSuccess

class SpnegoAuthenticatorMagnet(spnego: SpnegoAuthenticator)(implicit ec: ExecutionContext) 
    extends AuthMagnet[Token](extract(spnego).flatMap(onSuccess(_))) {
  override val directive: Directive1[Token] = extract(spnego).flatMap(onSuccess(_)).flatMap{
    case Left(rejection) => reject(rejection)
    case Right(token) => provide(token) & spnego.setSpnegoCookie(token)
  }
}

object SpnegoAuthenticatorMagnet {
  implicit def fromSpnegoAuthenticator(spnego: SpnegoAuthenticator)(implicit ec: ExecutionContext): AuthMagnet[Token] =
    new SpnegoAuthenticatorMagnet(spnego)
}
