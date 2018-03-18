package com.ruchij.models

import com.ruchij.services.kvstore.format.Formatter
import net.liftweb.json.{Extraction, JsonParser}
import org.joda.time.DateTime

case class AuthenticationToken(
    bearerToken: String,
    userId: String,
    expiryTimestamp: DateTime
)

object AuthenticationToken
{
  implicit object AuthenticationTokenFormatter extends Formatter[AuthenticationToken]
  {
    import com.ruchij.utils.Serializers._

    override def serialize(authenticationToken: AuthenticationToken): String =
      Extraction.decompose(authenticationToken).toString

    override def deserialize(stringValue: String): Option[AuthenticationToken] =
      for {
        jvalue <- JsonParser.parseOpt(stringValue)
        authenticationToken <- jvalue.extractOpt[AuthenticationToken]
      }
      yield authenticationToken
  }
}