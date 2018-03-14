package com.ruchij.exceptions

import com.ruchij.models.User

case class LiftMapperUserSaveException(user: User) extends Exception
{
  override def getMessage: String = s"Unable to save $user"
}
