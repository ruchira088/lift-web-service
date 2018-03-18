package bootstrap.liftweb

import java.util.concurrent.Executors

import com.ruchij.constants.ConfigValues
import com.ruchij.daos.{DatabaseConnectionManager, LiftMapperUserDao}
import com.ruchij.ecs.BlockingExecutionContext
import com.ruchij.services.UserService
import com.ruchij.services.hashing.BCryptPasswordHashingService
import com.ruchij.web.routes.{IndexRoute, UserRoute}

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}
import scala.concurrent.duration._
import scala.util.Try

class Boot {
  def postgresUrl(host: String, port: Int, name: String) =
    s"jdbc:postgresql://$host:$port/$name"

  def boot(): Unit =
    Try {
      implicit val executionContext: ExecutionContextExecutorService =
        ExecutionContext.fromExecutorService {
          Executors.newFixedThreadPool(ConfigValues.DEFAULT_EXECUTION_CONTEXT_THREAD_POOL_SIZE)
        }
      val blockingExecutionContext: BlockingExecutionContext = BlockingExecutionContext()

      val passwordHashingService = BCryptPasswordHashingService()(blockingExecutionContext)

      val result =
        for {
          userDao <- liftMapperUserDao()

          userService = UserService(userDao, passwordHashingService)

          _ = {
            IndexRoute.init()
            UserRoute.init(userService)
          }
        }
        yield (): Unit

      Await.ready(result, ConfigValues.FUTURE_TIMEOUT)
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