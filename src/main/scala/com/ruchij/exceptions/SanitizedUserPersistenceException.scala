package com.ruchij.exceptions

import com.ruchij.models.User

case class SanitizedUserPersistenceException(user: User) extends Exception
{
  override def getMessage: String = s"Unable to insert sanitized $user to the database."
}
