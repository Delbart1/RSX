package EXO3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Chat extends Thread {

	private String myName;
	private int port = 7654;
	private static String group = "224.0.0.2";

	public static void main(String[] args) throws IOException {

		int port = 7654;
		MulticastSocket s = new MulticastSocket();

		// SENDER THREAD

		Thread sender = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					byte buf[] = new byte[1024];
					Scanner scanner = new Scanner(System.in);
					buf = (scanner.nextLine()).getBytes();
					DatagramPacket pack = null;
					try {
						pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						s.send(pack);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

		// RECEIVER THREAD

		Thread receiver = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) {
					MulticastSocket s = null;
					String userID;
					try {
						s = new MulticastSocket(port);
						s.joinGroup(InetAddress.getByName(group));


						while (true) {

							byte buf[] = new byte[1024];
							DatagramPacket pack = new DatagramPacket(buf, buf.length);

							s.receive(pack);
							userID = pack.getAddress().getHostName();
							System.out.println(userID + " : " + new String(pack.getData()));
						}

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});

		sender.start();
		receiver.start();

	}
}
