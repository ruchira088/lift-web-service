package com.ruchij

import com.ruchij.daos.SlickUserDao
import slick.jdbc.JdbcBackend._

import scala.concurrent.ExecutionContext.Implicits.global

object App
{
  def main(args: Array[String]): Unit =
  {
  }

  def add(x: Int, y: Int): Int = x + y
}
