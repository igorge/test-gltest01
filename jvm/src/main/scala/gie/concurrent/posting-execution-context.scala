package gie.concurrent

import java.util.concurrent.{ConcurrentLinkedQueue, Executors}

import scala.concurrent.ExecutionContextExecutor

class PostingExecutionContext(private val enqueue: Runnable=>Unit) extends ExecutionContextExecutor {
    def reportFailure(t: Throwable): Unit = {
        t.printStackTrace()
    }

    def execute(command: Runnable): Unit = {
        enqueue(command)
    }
}


class PostingExecutionContextRunner {

    val queue = Executors.newSingleThreadExecutor()
    private def enqueue(runnable: Runnable): Unit = {
        queue.submit(runnable)
    }
    val executionContext = new PostingExecutionContext( enqueue )

}




