package com.assignment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/*
 *  Main application class to start the producer-consumer application. 
 */

public class ProducerConsumerApplication {

	private static final Logger log = Logger.getLogger(ProducerConsumerApplication.class.getName());

	public static void main(String[] args) {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
		CustomMessageQueue mQueue = new CustomMessageQueue(queue);

		Producer producer = new Producer(mQueue, 100);
		Consumer consumer = new Consumer(mQueue);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			log.severe("Interruption in Thread: " + e.getMessage());
		}

		log.info("Total messages processed successfully: " + mQueue.getSuccessCount());
		log.info("Total errors encountered: " + mQueue.getErrorCount());

	}
}