package cn.jy.stork;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class StorkTestClient {
	public static void main(String[] args) throws IOException, InterruptedException {
		for (int i = 0; i < 5; i++) {
			Socket client = new Socket();
			client.connect(new InetSocketAddress(2317));
			Thread.sleep(3000);
			client.close();

		}
	}
}
