package com.ruchij.daos

import com.ruchij.models.User

import scala.concurrent.Future
import scalaz.OptionT

trait UserDao
{
  def insert(user: User): Future[User]

  def getByUsername(id: String): OptionT[Future, User]

  def init(): Future[_] = Future.successful((): Unit)
}
