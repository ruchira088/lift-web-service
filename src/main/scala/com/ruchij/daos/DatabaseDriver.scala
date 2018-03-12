package com.ruchij.daos

import java.sql.Driver

trait DatabaseDriver[A <: Driver]
{
  def getDriver: Class[A]

  def driverClassName: String = getDriver.getName
}
