package bootstrap.liftweb

import akka.actor.ActorSystem
import com.eed3si9n.BuildInfo
import com.ruchij.daos.{DatabaseConnectionManager, LiftMapperUserDao, UserDao}
import com.ruchij.models.User
import com.ruchij.web.routes.IndexRoute
import org.joda.time.DateTime

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.Try
import scalaz.OptionT
import scalaz.std.scalaFuture._

class Boot {
  def postgresUrl(host: String, port: Int, name: String) =
    s"jdbc:postgresql://$host:$port/$name"

  def boot(): Unit =
    Try {
      implicit val actorSystem: ActorSystem = ActorSystem(BuildInfo.name)
      implicit val executionContext: ExecutionContext = actorSystem.dispatcher

      IndexRoute.init()

      val result = for {
        userDao <- OptionT {
          liftMapperUserDao().map[Option[UserDao]](Some(_))
        }

        user <- userDao.getByUsername("ruchira")
      }
      yield user

      println(Await.result(result.run, 1 minute))
    }
      .fold(
        exception => {
          System.err.println(exception.getMessage)
          System.exit(1)
        },
        _ => println("Successfully started LiftWeb application.")
      )

  def liftMapperUserDao()(implicit executionContext: ExecutionContext): Future[LiftMapperUserDao] =
    for {
      databaseConnectionManager <- Future.fromTry(DatabaseConnectionManager.postgres())
      liftMapperUserDao = LiftMapperUserDao(databaseConnectionManager)
      _ <- liftMapperUserDao.init()
    }
      yield liftMapperUserDao
}