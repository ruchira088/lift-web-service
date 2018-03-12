package bootstrap.liftweb

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import com.eed3si9n.BuildInfo
import com.ruchij.constants.EnvValueNames._
import com.ruchij.daos.{DatabaseConnectionManager, MapperUser}
import com.ruchij.utils.ConfigUtils._
import com.ruchij.web.routes.{IndexRoute, UserRoute}
import net.liftweb.db.DB
import net.liftweb.mapper.Schemifier
import net.liftweb.util.DefaultConnectionIdentifier

import scala.concurrent.ExecutionContext
import scala.util.Try

class Boot
{
  def boot =
  {
//    implicit val actorSystem: ActorSystem = ActorSystem("hello-world")
//    implicit val executionContext: ExecutionContext = actorSystem.dispatcher
//
//    implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

    println("Hello World")
    IndexRoute.init()

    val connectionManager: Try[DatabaseConnectionManager] =
      for {
        databaseUrl <- envValue(POSTGRES_URL)
        user <- envValue(POSTGRES_USER)
        password <- envValue(POSTGRES_PASSWORD)
      }
      yield DatabaseConnectionManager.postgres(databaseUrl, user, password)

    DB.defineConnectionManager(
      DefaultConnectionIdentifier,
      connectionManager.get
    )

    println("Here")

    Schemifier.schemify(true, Schemifier.infoF _, MapperUser)

    println("Here 1")

    val user = MapperUser.create

    println(user)

    println(Try{
      user
        .username("sample-username")
        .email("ruchira088@gmail.com")
    }
    )
    println("Here 2")

    user.save()

    println("Success")
  }
}