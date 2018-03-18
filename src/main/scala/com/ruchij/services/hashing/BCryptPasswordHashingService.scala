package com.ruchij.services.hashing

import com.ruchij.ecs.BlockingExecutionContext
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future}

class BCryptPasswordHashingService()(implicit executionContext: ExecutionContext) extends PasswordHashingService
{
  override def hash(password: String): Future[String] =
    Future {
      BCrypt.hashpw(password, BCrypt.gensalt())
    }

  override def checkPassword(hash: String, candidate: String): Future[Boolean] =
    Future {
      BCrypt.checkpw(candidate, hash)
    }
}

object BCryptPasswordHashingService
{
  def apply()(implicit blockingExecutionContext: BlockingExecutionContext): BCryptPasswordHashingService =
    new BCryptPasswordHashingService()
}