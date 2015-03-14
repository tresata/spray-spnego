package com.tresata.spray.spnego

import scala.concurrent.ExecutionContext
import akka.actor.{ ActorSystem, Props, ActorLogging }
import akka.io.IO
import spray.can.Http
import spray.routing.HttpServiceActor
import com.tresata.spray.spnego.SpnegoAuthenticatorMagnet._

class RestInterface extends HttpServiceActor with ActorLogging {
  implicit def executionContext: ExecutionContext = actorRefFactory.dispatcher

  val authenticator = SpnegoAuthenticator()

  def receive = runRoute(route)

  val route = logRequestResponse("debug") {
    authenticate(authenticator) { token =>
      get{
        path("ping") {
          complete(s"pong for user ${token.principal}")
        }
      }
    }
  }
}

object Main extends App {
  implicit val system = ActorSystem()

  val handler = system.actorOf(Props(new RestInterface()), "restInterface")
  IO(Http) ! Http.Bind(handler, interface = "0.0.0.0", port = 12345)
}
