package com.ruchij.services.kvstore

import akka.actor.ActorSystem
import com.ruchij.Environment
import com.ruchij.constants.EnvValueNames._
import com.ruchij.services.kvstore.format.{Deserializer, Serializer}
import com.ruchij.utils.ConfigUtils.envValue
import com.ruchij.utils.ScalaUtils._
import redis.RedisClient
import scalaz.OptionT
import scalaz.std.scalaFuture.futureInstance

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class RedisKeyValueStore(redisClient: RedisClient)
                        (implicit executionContext: ExecutionContext) extends KeyValueStore
{
  override def insert[A](key: String, value: A)(implicit serializer: Serializer[A]): Future[_] =
    redisClient.set(key, serializer.serialize(value))

  override def fetch[A](key: String)(implicit deserializer: Deserializer[A]): OptionT[Future, A] =
    for {
      stringValue <- OptionT(redisClient.get[String](key))

      value <- deserializer.deserialize(stringValue)
        .fold[OptionT[Future, A]](OptionT.none)(OptionT.some(_))
    }
    yield value

  override def remove[A](key: String)(implicit deserializer: Deserializer[A]): OptionT[Future, A] =
    for {
      value <- fetch(key)

      _ <- redisClient.del(key)
    }
    yield value

  override def init(): Future[_] = redisClient.ping()
}

object RedisKeyValueStore
{
  def apply()(implicit environment: Environment, actorSystem: ActorSystem, executionContext: ExecutionContext): Try[RedisKeyValueStore] =
    for {
      host <- envValue(REDIS_HOST)
      port <- envValue(REDIS_PORT).flatMap(portString => Try(portString.toInt))
      password = envValue(REDIS_PASSWORD).toOption
    }
    yield RedisKeyValueStore(host, port, password)

  def apply(host: String, port: Int, password: Option[String])(implicit actorSystem: ActorSystem, executionContext: ExecutionContext) =
    new RedisKeyValueStore(RedisClient(host, port, password))
}
