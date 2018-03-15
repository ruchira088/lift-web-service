package com.ruchij.utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object ScalaUtils
{
  def predicate(booleanValue: Boolean, exception: => Exception): Try[Unit] =
    if (booleanValue) Success((): Unit) else Failure(exception)

  def fromOption[A](optionValue: Option[A], exception: => Exception): Try[A] =
    optionValue.fold[Try[A]](Failure(exception))(Success(_))

  implicit def fromTry[A](tryValue: Try[A]): Future[A] =
    Future.fromTry(tryValue)
}
