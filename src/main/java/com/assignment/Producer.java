package com.assignment;

import java.util.logging.Logger;

/*
 * Producer class that generates messages and puts them into the message queue. 
 */

public class Producer implements Runnable {

	private static final Logger log = Logger.getLogger(Producer.class.getName());
	private final CustomMessageQueue mQueue;
	private final int noOfMsgs;

	public Producer(CustomMessageQueue mQueue, int noOfMsgs) {
		this.mQueue = mQueue;
		this.noOfMsgs = noOfMsgs;
	}

	@Override
	public void run() {

		for (int i = 0; i < noOfMsgs; i++) {
			String message = "Message :" + i;
			try {
				mQueue.produce(message);
				log.info("Produced: " + message);
			} catch (InterruptedException e) {
				log.severe("Interruption in producer : " + e.getMessage());
			}
		}

	}

}