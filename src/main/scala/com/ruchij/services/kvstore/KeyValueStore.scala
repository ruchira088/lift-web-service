package com.ruchij.services.kvstore

import com.ruchij.services.kvstore.format.{Deserializer, Serializer}
import scalaz.OptionT

import scala.concurrent.Future

trait KeyValueStore
{
  def init(): Future[_] = Future.successful()

  def insert[A](key: String, value: A)(implicit serializer: Serializer[A]): Future[_]

  def fetch[A](key: String)(implicit deserializer: Deserializer[A]): OptionT[Future, A]

  def remove[A](key: String)(implicit deserializer: Deserializer[A]): OptionT[Future, A]
}
