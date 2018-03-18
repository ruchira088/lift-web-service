package com.ruchij.exceptions

case class UserNotFoundException(username: String) extends Exception
{
  override def getMessage: String = s"""User NOT found: "$username""""
}