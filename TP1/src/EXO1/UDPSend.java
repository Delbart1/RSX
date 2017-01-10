package EXO1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class UDPSend extends Thread {

	public static void main(String[] args) throws IOException {
		
		int port = 7654;
		String group = "224.0.0.1";
		MulticastSocket s = new MulticastSocket();
		
		DatagramSocket serverSocket = new DatagramSocket();
		byte[] sendData = new byte[1024];
		InetAddress IPAddress = InetAddress.getByName(args[0]);

		String message = "java Receive UDP " + serverSocket.getLocalPort();
		sendData = message.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		serverSocket.send(sendPacket);
		

	}
}
