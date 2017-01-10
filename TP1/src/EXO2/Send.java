package EXO2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Send extends Thread {

	public static void main(String[] args) throws IOException {
		Scanner scanner;
		String Moi;
		int port = 7654;
		String group = "224.0.0.1";
		MulticastSocket s = new MulticastSocket();

		System.out.println("Your name :");
		scanner = new Scanner(System.in);
		Moi = scanner.next();

		byte buf[] = (Moi + " a rejoint le chat").getBytes();
		DatagramPacket pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
		s.send(pack);

		while (true) {
			buf = new byte[1024];
			scanner = new Scanner(System.in);
			buf = (Moi + " : " + scanner.nextLine()).getBytes();
			pack = new DatagramPacket(buf, buf.length, InetAddress.getByName(group), port);
			s.send(pack);
		}

	}
}
