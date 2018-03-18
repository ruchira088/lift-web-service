package com.ruchij.services

import com.ruchij.daos.UserDao
import com.ruchij.models.{AuthenticationToken, User}

import scala.concurrent.Future

class AuthenticationService(userDao: UserDao)
{
  def login(username: String, password: String): Future[AuthenticationToken] =
    ???

  def authenticate(tokenId: String): Future[User] = ???



}
