package com.ruchij.daos

import java.sql.{Connection, DriverManager}

import com.ruchij.constants.EnvValueNames._
import com.ruchij.utils.ConfigUtils.envValue
import net.liftweb.common.{Box, Full}
import net.liftweb.db.ConnectionManager
import net.liftweb.util.ConnectionIdentifier

import scala.util.Try

class DatabaseConnectionManager(databaseDriver: DatabaseDriver[_])(databaseUrl: String, user: String, password: String)
  extends ConnectionManager
{
  override def newConnection(name: ConnectionIdentifier): Box[Connection] =
    Full {
      Class.forName(databaseDriver.driverClassName)
      DriverManager.getConnection(databaseUrl, user, password)
    }

  override def releaseConnection(conn: Connection): Unit =
    conn.close()
}

object DatabaseConnectionManager
{
  private def postgres(databaseUrl: String, user: String, password: String) =
    new DatabaseConnectionManager(PostgresDriver)(databaseUrl, user, password)

  private def postgresUrl(host: String, port: Int, name: String) =
    s"jdbc:postgresql://$host:$port/$name"

  def postgres(): Try[DatabaseConnectionManager] =
    for {
      host <- envValue(POSTGRES_SERVER)
      port <- envValue(POSTGRES_PORT).flatMap(portString => Try(portString.toInt))
      name <- envValue(POSTGRES_DB)
      user <- envValue(POSTGRES_USER)
      password <- envValue(POSTGRES_PASSWORD)
    }
    yield DatabaseConnectionManager.postgres(postgresUrl(host, port, name), user, password)
}
