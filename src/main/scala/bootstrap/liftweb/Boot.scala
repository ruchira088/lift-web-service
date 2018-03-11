package bootstrap.liftweb

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import com.eed3si9n.BuildInfo
import com.ruchij.web.routes.{IndexRoute, UserRoute}

import scala.concurrent.ExecutionContext

class Boot
{
  def boot =
  {
//    implicit val actorSystem: ActorSystem = ActorSystem("hello-world")
//    implicit val executionContext: ExecutionContext = actorSystem.dispatcher

    implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

    println("Hello World")
    IndexRoute.init()
//    UserRoute.init()
  }
}