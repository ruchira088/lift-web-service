package com.ruchij.ecs

import scala.concurrent.ExecutionContextExecutorService

class BlockingExecutionContextImpl(executionContextExecutorService: ExecutionContextExecutorService)
  extends BlockingExecutionContext
{
  override def execute(runnable: Runnable): Unit =
    executionContextExecutorService.execute(runnable)

  override def reportFailure(cause: Throwable): Unit =
    executionContextExecutorService.reportFailure(cause)
}