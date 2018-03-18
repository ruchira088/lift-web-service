package com.ruchij.ecs

import java.util.concurrent.Executors

import com.ruchij.constants.ConfigValues

import scala.concurrent.ExecutionContext

trait BlockingExecutionContext extends ExecutionContext

object BlockingExecutionContext
{
  def apply(threadPoolSize: Int = ConfigValues.BLOCKING_EXECUTION_CONTEXT_THREAD_POOL_SIZE): BlockingExecutionContext =
    new BlockingExecutionContextImpl(
      ExecutionContext.fromExecutorService {
        Executors.newFixedThreadPool(threadPoolSize)
      }
    )
}