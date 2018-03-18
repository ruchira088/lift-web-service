package com.ruchij.services.kvstore.format

trait Serializer[A]
{
  def serialize(value: A): String
}
