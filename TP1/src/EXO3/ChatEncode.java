package EXO3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class ChatEncode extends Thread {

	private String myName;
	private int port = 7654;
	private final static String userName = "Jonathan";
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

					String encodage;

					encodage = getKey() + ":" + userName + ":" + encode(scanner.nextLine(), getKey());
					buf = encodage.getBytes();
					DatagramPacket pack = null;
					try {
						pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
					try {
						s.send(pack);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});

		// RECEIVER THREAD

		Thread receiver = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Welcome !");
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

							String receiveMess = new String(pack.getData(), 0, pack.getLength());
							String[] tabMess = receiveMess.split(":");

							System.out.println(tabMess[1] + " : " + decode(tabMess[2], Integer.parseInt(tabMess[0])));
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

	public static String encode(String message, int shift) {
		String res = "";
		for (int i = 0; i < message.length(); i++) {
			res += (char) (int) (message.charAt(i) + shift);
		}
		return res;
	}

	public static String decode(String message, int shift) {
		String res = "";
		for (int i = 0; i < message.length(); i++) {
			res += (char) (int) (message.charAt(i) - shift);
		}
		return res;
	}

	public static int getKey() {
		String mac = "";
		byte[] macAddress = null;

		try {
			InetAddress address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Enumeration<NetworkInterface> network = null;
		try {
			network = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		while (network.hasMoreElements()) {
			NetworkInterface net = network.nextElement();
			try {
				macAddress = net.getHardwareAddress();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			if (macAddress != null) {
				mac = "";
				for (int i = 0; i < macAddress.length; i++) {
					mac += String.format("%02X%s", macAddress[i], (i < macAddress.length - 1) ? "-" : "");
				}
			}
		}
		String[] macTab = mac.split("-");
		return Integer.parseInt(macTab[macTab.length - 1], 16);

	}
}
