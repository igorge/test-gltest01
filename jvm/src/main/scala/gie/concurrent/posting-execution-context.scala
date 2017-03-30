package gie.concurrent

import java.util.concurrent.{ConcurrentLinkedQueue, Executors}

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




