package com.ruchij.services

import com.ruchij.constants.ConfigValues._
import com.ruchij.daos.UserDao
import com.ruchij.exceptions.{InvalidCredentialsException, UserNotFoundException}
import com.ruchij.models.{AuthenticationToken, User}
import com.ruchij.services.hashing.PasswordHashingService
import com.ruchij.services.kvstore.KeyValueStore
import com.ruchij.utils.GeneralUtils.uuid
import com.ruchij.utils.ScalaUtils._
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}

class AuthenticationService(userDao: UserDao, passwordHashingService: PasswordHashingService, keyValueStore: KeyValueStore)
{
  def login(username: String, password: String)
           (implicit executionContext: ExecutionContext): Future[AuthenticationToken] =
    for {
      user <- userDao.getByUsername(username)
        .getOrElseF(Future.failed(UserNotFoundException(username)))

      hashedPassword <- fromOption(user.password, ???)

      isMatch <- passwordHashingService.checkPassword(hashedPassword, password)
      _ <- predicate(isMatch, InvalidCredentialsException)

      authenticationToken = AuthenticationToken(
        uuid(),
        user.id,
        DateTime.now().plus(BEARER_TOKEN_TIMEOUT.toMillis)
      )

      _ <- keyValueStore.insert(authenticationToken.bearerToken, authenticationToken)
    }
    yield authenticationToken

  def authenticate(tokenId: String): Future[User] = ???
}
