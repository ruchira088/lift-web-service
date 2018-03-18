package com.ruchij.web.responses

import com.ruchij.models.User
import net.liftweb.json.{Extraction, JValue}

case class UserResponse(id: String, username: String, email: String)

object UserResponse
{
  import com.ruchij.utils.Serializers._

  def fromUser(user: User): UserResponse =
    UserResponse(user.id, user.username, user.email)

  implicit def toJValue(userResponse: UserResponse): JValue =
    Extraction.decompose(userResponse)
}