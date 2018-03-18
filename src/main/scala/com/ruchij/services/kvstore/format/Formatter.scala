package com.ruchij.services.kvstore.format

trait Formatter[A] extends Deserializer[A] with Serializer[A]
