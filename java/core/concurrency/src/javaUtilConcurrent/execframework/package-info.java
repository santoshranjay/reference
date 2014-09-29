package javaUtilConcurrent.execframework;
/**
 * Executor framework for asynchronous task execution that supports a wide variety of task
 * execution policy.
 * (decouple task submission from the task execution)
 * 
 * Executor is based on the producer-consumer pattern, where activities that submit tasks
 * are the producers and the threads that execute tasks are the consumers.
 * 
 * ?difference between submit and execute
 * Method submit extends base method Executor.execute(java.lang.Runnable) by creating and returning a Future that 
 * can be used to cancel execution and/or wait for completion.
 * ? awaitTermination - how to simulate the block for current thread interruption.
 * 
 */
