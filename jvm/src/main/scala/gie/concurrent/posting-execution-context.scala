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


class PostingExecutionContextRunner extends AutoCloseable {

    private val queue = Executors.newSingleThreadExecutor()
    private def enqueue(runnable: Runnable): Unit = {
        queue.submit(runnable)
    }
    val executionContext = new PostingExecutionContext( enqueue )

    def close(): Unit ={
        enqueue(new Runnable {
            def run(): Unit = {
                queue.shutdown()
            }
        })
    }
}




