package gie.concurrent

import java.util.concurrent.ConcurrentLinkedQueue

import scala.concurrent.ExecutionContextExecutor

class PostingExecutionContext(private val enqueue: Runnable=>Unit) extends ExecutionContextExecutor {
    def reportFailure(t: Throwable): Unit = {
        t.printStackTrace()
    }

    def execute(command: Runnable): Unit = {
        enqueue(command)
    }
}


class PackagedPostingExecutionContext {

    val queue = new ConcurrentLinkedQueue[Runnable]()
    val executionContext = new PostingExecutionContext( queue.add(_) )

    def run(): Boolean ={
        val runnable = queue.poll()

        if(runnable eq null) {
            false
        } else {
            runnable.run()
            true
        }

    }

    def runAll(): Unit ={
        while( run() ){
            
        }
    }

}


