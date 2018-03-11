package com.ruchij.models

import org.joda.time.DateTime

case class User(
   id: String,
   timeStamp: DateTime,
   username: String,
   password: Option[String],
   email: String
) {
  self =>

  def sanitize = self.copy(password = None)
}