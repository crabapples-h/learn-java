package org.springblade.dns;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {


	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("/Users/mshe/Desktop/test.txt", "rw");
		FileChannel channel = file.getChannel();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024);
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();


		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put(Thread.currentThread().getName() + "\n");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put(Thread.currentThread().getName() + "\n");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					queue.put(Thread.currentThread().getName() + "\n");
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}).start();


		try {
			int i = 0;
			long lastEmpTime = -1;
			while (true) {
				String poll = queue.poll();
				if (null != poll) {
					buffer.put(poll.getBytes());
					i++;
				}
				if (queue.isEmpty()) {
					if (lastEmpTime == -1) {
						lastEmpTime = System.currentTimeMillis();
					} else if (System.currentTimeMillis() - lastEmpTime > 1000) {
						break;
					}
				}
			}
			System.err.println(i);
		} finally {
			try {
				file.close();
				System.out.println("file closed");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}


	}
}
