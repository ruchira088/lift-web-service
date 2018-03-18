package com.ruchij.services.hashing

import scala.concurrent.Future

trait PasswordHashingService
{
  def hash(password: String): Future[String]

  def checkPassword(hash: String, candidate: String): Future[Boolean]
}
