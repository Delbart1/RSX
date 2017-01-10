package EXO2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receive extends Thread {

	public static void main(String[] args){

		int port = 7654;
		String group = "224.0.0.1";
		MulticastSocket s;
		try {
			s = new MulticastSocket(port);
			s.joinGroup(InetAddress.getByName(group));

			while (true) {
				byte buf[] = new byte[1024];
				DatagramPacket pack = new DatagramPacket(buf, buf.length);
				s.receive(pack);
				System.out.println(new String(pack.getData()));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
