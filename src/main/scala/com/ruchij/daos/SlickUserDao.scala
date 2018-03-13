package com.ruchij.daos

import java.sql.Timestamp

import com.ruchij.models.User
import org.joda.time.DateTime
import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.{ExecutionContext, Future}
import scalaz.OptionT

class SlickUserDao(db: Database)(implicit executionContext: ExecutionContext)
  extends UserDao
{
  import SlickUserDao._

  class UserTable(tag: Tag) extends Table[User](tag, TABLE_NAME)
  {
    def id = column[String]("id")
    def timeStamp = column[DateTime]("timestamp")
    def username = column[String]("username")
    def password = column[Option[String]]("password")
    def email = column[String]("email")

    override def * = (id, timeStamp, username, password, email) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[UserTable]

  override def insert(user: User): Future[User] =
    db.run(users += user).map(_ => user)

  override def getByUsername(username: String): OptionT[Future, User] =
    OptionT {
      db.run {
        users
          .filter(_.username === username)
          .sortBy(_.timeStamp.desc)
          .result
          .headOption
      }
    }
}

object SlickUserDao
{
  val TABLE_NAME = "users"

  val DB_CONFIG = "postgres"

  def apply(db: Database)(implicit executionContext: ExecutionContext): SlickUserDao =
    new SlickUserDao(db)

  implicit def jodaTimeColumnType: JdbcType[DateTime] with BaseTypedType[DateTime] =
    MappedColumnType.base[DateTime, Timestamp](
      dateTime => new Timestamp(dateTime.getMillis),
      timeStamp => new DateTime(timeStamp.getTime)
    )
}
