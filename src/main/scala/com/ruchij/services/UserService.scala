package com.ruchij.services

import com.ruchij.daos.UserDao
import com.ruchij.models.User
import com.ruchij.services.hashing.PasswordHashingService
import com.ruchij.utils.GeneralUtils._
import com.ruchij.web.requests.UserRequest
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}

class UserService(userDao: UserDao, passwordHashingService: PasswordHashingService)
                 (implicit executionContext: ExecutionContext)
{
  def newUser(userRequest: UserRequest): Future[User] =
    for {
      hashedPassword <- passwordHashingService.hash(userRequest.password)

      user <- userDao.insert(
        User(uuid(),
          DateTime.now(),
          userRequest.username,
          Some(hashedPassword),
          userRequest.email
        )
      )
    }
    yield user.sanitize
}

object UserService
{
  def apply(userDao: UserDao, passwordHashingService: PasswordHashingService)
           (implicit executionContext: ExecutionContext): UserService =
    new UserService(userDao, passwordHashingService)
}