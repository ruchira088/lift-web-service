package bootstrap.liftweb

import akka.actor.ActorSystem
import com.eed3si9n.BuildInfo
import com.ruchij.constants.EnvValueNames._
import com.ruchij.daos.{DatabaseConnectionManager, MapperUser}
import com.ruchij.utils.ConfigUtils._
import com.ruchij.web.routes.IndexRoute
import net.liftweb.db.DB
import net.liftweb.mapper.Schemifier
import net.liftweb.util.DefaultConnectionIdentifier

import scala.concurrent.ExecutionContext
import scala.util.Try

class Boot
{
  def postgresUrl(host: String, port: Int, name: String) =
    s"jdbc:postgresql://$host:$port/$name"

  def boot(): Unit =
    Try {
      implicit val actorSystem: ActorSystem = ActorSystem(BuildInfo.name)
      implicit val executionContext: ExecutionContext = actorSystem.dispatcher

      IndexRoute.init()

      val connectionManager: Try[DatabaseConnectionManager] =
        for {
          host <- envValue(POSTGRES_SERVER)
          port <- envValue(POSTGRES_PORT).flatMap(portString => Try(portString.toInt))
          name <- envValue(POSTGRES_DB)
          user <- envValue(POSTGRES_USER)
          password <- envValue(POSTGRES_PASSWORD)
        }
        yield DatabaseConnectionManager.postgres(postgresUrl(host, port, name), user, password)

      DB.defineConnectionManager(
        DefaultConnectionIdentifier,
        connectionManager.get
      )

      Schemifier.schemify(true, Schemifier.infoF _, MapperUser)

      MapperUser.create
        .username("sample-username")
        .email("ruchira088@gmail.com")
        .save()

    }
      .fold(
        exception => System.err.println(exception.getMessage),
        _ => println("Successfully started LiftWeb application.")
      )
}