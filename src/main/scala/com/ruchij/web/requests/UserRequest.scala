package com.ruchij.web.requests

import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JValue

case class UserRequest(username: String, password: String, email: String)

object UserRequest
{
  import com.ruchij.web.utils.Serializers._

  implicit def toJValue(userRequest: UserRequest): JValue =
    Extraction.decompose(userRequest)

  def unapply(jValue: JValue): Option[UserRequest] =
    jValue.extractOpt[UserRequest]
}