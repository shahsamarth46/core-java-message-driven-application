package com.assignment;

import java.util.logging.Logger;

/*
 * Consumer class that takes messages from the message queue and processes them. 
 * It increments error count in case of processing errors.
 */

public class Consumer implements Runnable {

	private static final Logger log = Logger.getLogger(Consumer.class.getName());
	private final CustomMessageQueue mQueue;

	public Consumer(CustomMessageQueue mQueue) {
		this.mQueue = mQueue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				String message = mQueue.consume();
				log.info("Consumed : " + message);
			} catch (InterruptedException e) {
				log.severe("Interruption in producer : " + e.getMessage());
				break;
			} catch (Exception e) {

				mQueue.incrementErrorCount();
				log.severe("Error processing message: " + e.getMessage());
			}
		}
	}

}
