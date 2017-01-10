package EXO1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class UDPReceive extends Thread {

	public static void main(String[] args) throws Exception {

		int port = 7654;
		String group = "224.0.0.1";
		MulticastSocket s = new MulticastSocket();
		
		DatagramSocket clientSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[1024];

		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		System.out.println(clientSocket.getLocalPort());

		String message = new String(receivePacket.getData());
		System.out.println(message);

		clientSocket.close();

	}
}
