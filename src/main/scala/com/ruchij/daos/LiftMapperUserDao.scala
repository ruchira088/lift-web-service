package com.ruchij.daos
import java.sql.Timestamp

import com.ruchij.exceptions.LiftMapperUserSaveException
import com.ruchij.models.User
import com.ruchij.utils.ScalaUtils._
import net.liftweb.db.DB
import net.liftweb.util.DefaultConnectionIdentifier

import scala.concurrent.{ExecutionContext, Future}
import scalaz.OptionT

class LiftMapperUserDao(databaseConnectionManager: DatabaseConnectionManager)(implicit executionContext: ExecutionContext) extends UserDao
{
  override def insert(user: User): Future[User] =
    for {
      result <- Future {
        MapperUser.create
          .id(user.id)
          .timeStamp(new Timestamp(user.timeStamp.getMillis))
          .username(user.username)
          .email(user.email)
          .save()
      }

      _ <- predicate(result, LiftMapperUserSaveException(user))
    }
    yield user


  override def init(): Future[_] =
    Future {
      DB.defineConnectionManager(
        DefaultConnectionIdentifier,
        databaseConnectionManager
      )

      MapperUser.init()
    }

  override def getByUsername(id: String): OptionT[Future, User] = ???
}

object LiftMapperUserDao
{
  def apply(databaseConnectionManager: DatabaseConnectionManager)
           (implicit executionContext: ExecutionContext): LiftMapperUserDao =
    new LiftMapperUserDao(databaseConnectionManager)
}