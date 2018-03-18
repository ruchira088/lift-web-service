package com.ruchij.utils

import com.ruchij.Environment
import com.ruchij.exceptions.EnvValueUndefinedException

import scala.util.{Failure, Success, Try}

object ConfigUtils
{
  def envValue(name: String)(implicit environment: Environment): Try[String] =
    environment.get(name).fold[Try[String]](Failure(EnvValueUndefinedException(name)))(Success(_))
}