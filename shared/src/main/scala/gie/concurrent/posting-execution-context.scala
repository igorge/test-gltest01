package gie.concurrent

import scala.concurrent.ExecutionContextExecutor

class PostingExecutionContext(private val enqueue: Runnable=>Unit) extends ExecutionContextExecutor {
    def reportFailure(t: Throwable): Unit = {
        t.printStackTrace()
    }

    def execute(command: Runnable): Unit = {
        enqueue(command)
    }
}

