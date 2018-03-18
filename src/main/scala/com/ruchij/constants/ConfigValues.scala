package com.ruchij.constants

import scala.concurrent.duration._

object ConfigValues
{
  val BEARER_TOKEN_TIMEOUT: FiniteDuration = 30 minutes

  val FUTURE_TIMEOUT: FiniteDuration = 30 seconds

  val BLOCKING_EXECUTION_CONTEXT_THREAD_POOL_SIZE = 10
  val DEFAULT_EXECUTION_CONTEXT_THREAD_POOL_SIZE = 30
}
