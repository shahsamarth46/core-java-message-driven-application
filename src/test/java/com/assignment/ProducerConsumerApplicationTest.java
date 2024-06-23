package com.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ProducerConsumerApplication.
 */

public class ProducerConsumerApplicationTest {

	private CustomMessageQueue mQueue;

	@BeforeEach
	public void setUp() {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>(10);
		mQueue = new CustomMessageQueue(queue);
	}

	@Test
	public void testSuccessfulMessageProcessing() throws InterruptedException {
		Producer producer = new Producer(mQueue, 5);
		Consumer consumer = new Consumer(mQueue);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		producerThread.join();
		consumerThread.interrupt(); // Stop the consumer

		assertEquals(5, mQueue.getSuccessCount());
		assertEquals(0, mQueue.getErrorCount());
	}

	@Test
	public void testErrorMessageProcessing() throws InterruptedException {

		mQueue.produce("error-message");

		Consumer consumer = new Consumer(mQueue);
		Thread consumerThread = new Thread(consumer);

		consumerThread.start();
		consumerThread.interrupt(); // Stop the consumer

		assertEquals(0, mQueue.getSuccessCount());
		assertEquals(1, mQueue.getErrorCount());
	}

}
