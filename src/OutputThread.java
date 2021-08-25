import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class OutputThread extends Thread {
	Socket socket;		// socket is joining to the communication
	JTextArea txt;		// text-area contains communicated message
	BufferedReader bf;	// in put buffer of the socket
	String sender;		// sender, a site of the communication
	String receiver;	// receiver, other site of the communication
	
	public OutputThread(Socket s, JTextArea txt, String sender, String receiver) {
		super();
		this.socket = s;
		this.txt = txt;
		this.sender = sender;
		this.receiver = receiver;
		
		try {
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "��Ʈ��ũ ����");
			System.exit(0);
		}
	}
	
	public void run() {
		while(true) {
			try {
				if(socket != null) {
					String msg = "";	// input stream���κ��� �Է��� ������ ��������
					if((msg = bf.readLine()) != null && msg.length() > 0) {		// �����ΰ� �Է��ߴٸ�
						txt.append("\n" + receiver + ": " + msg);
					}
					sleep(1000);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
