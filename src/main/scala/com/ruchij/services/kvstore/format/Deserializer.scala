package com.ruchij.services.kvstore.format

trait Deserializer[A]
{
  def deserialize(stringValue: String): Option[A]
}
