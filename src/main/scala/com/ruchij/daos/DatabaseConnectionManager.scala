package com.ruchij.daos

import java.sql.{Connection, DriverManager}

import net.liftweb.common.{Box, Full}
import net.liftweb.db.ConnectionManager
import net.liftweb.util.ConnectionIdentifier

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
  def postgres(databaseUrl: String, user: String, password: String) =
    new DatabaseConnectionManager(PostgresDriver)(databaseUrl, user, password)
}
