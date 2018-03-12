package com.ruchij.daos
import org.postgresql

object PostgresDriver extends DatabaseDriver[postgresql.Driver]
{
  override def getDriver =
    classOf[org.postgresql.Driver]
}
