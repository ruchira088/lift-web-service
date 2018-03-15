package com.ruchij.daos
import java.sql.Timestamp

import com.ruchij.exceptions.{LiftMapperUserSaveException, SanitizedUserPersistenceException}
import com.ruchij.models.User
import com.ruchij.utils.ScalaUtils._
import net.liftweb.db.DB
import net.liftweb.mapper.By
import net.liftweb.util.DefaultConnectionIdentifier
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}
import scalaz.OptionT

class LiftMapperUserDao(databaseConnectionManager: DatabaseConnectionManager)(implicit executionContext: ExecutionContext) extends UserDao
{
  override def insert(user: User): Future[User] =
    for {
      userPassword <- fromTry(fromOption(user.password, SanitizedUserPersistenceException(user)))

      result <- Future {
        MapperUser.create
          .id(user.id)
          .timeStamp(new Timestamp(user.timeStamp.getMillis))
          .username(user.username)
          .password(userPassword)
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

  override def getByUsername(username: String): OptionT[Future, User] =
    OptionT {
      Future {
        MapperUser.find(By(MapperUser.username, username)).toOption
          .map(mappedUser =>
            User(
              mappedUser.id.get,
              new DateTime(mappedUser.timeStamp.get.getTime),
              mappedUser.username.get,
              Option(mappedUser.password.get),
              mappedUser.email.get
            )
          )
      }
    }
}

object LiftMapperUserDao
{
  def apply(databaseConnectionManager: DatabaseConnectionManager)
           (implicit executionContext: ExecutionContext): LiftMapperUserDao =
    new LiftMapperUserDao(databaseConnectionManager)
}