package com.assignment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * CustomMessageQueue class encapsulates a blocking queue and provides methods for producing and consuming messages.
 * It also maintains counters for successful and failed message processing.
 */

public class CustomMessageQueue {

	// As we are dealing with multi-threaded environment we need to go with
	// BlockingQueue interface
	private final BlockingQueue<String> queue;

	// As we are dealing with multi-threaded environment, we need to use
	// AtomicInteger.
	private final AtomicInteger sucessCount = new AtomicInteger();
	private final AtomicInteger errorCount = new AtomicInteger();

	public CustomMessageQueue(BlockingQueue<String> q) {
		queue = q;
	}

	public void produce(String msg) throws InterruptedException {
		queue.put(msg);
	}

	public String consume() throws InterruptedException {
		String msg = queue.take();
		if (msg.contains("error")) {
			throw new RuntimeException("Peocessing error");
		}
		sucessCount.incrementAndGet();
		return msg;
	}

	public void incrementErrorCount() {
		errorCount.incrementAndGet();
	}

	public int getErrorCount() {
		return errorCount.get();
	}

	public int getSuccessCount() {
		return sucessCount.get();
	}

}