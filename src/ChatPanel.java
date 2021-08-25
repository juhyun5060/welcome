import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel {
	
	Socket socket = null;
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread t = null;
	String sender;
	String receiver;
	JTextArea txtMessages;
	
	/**
	 * Create the panel.
	 */
	public ChatPanel(Socket s, String sender, String receiver) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea txtMessage = new JTextArea();
		scrollPane.setViewportView(txtMessage);
		
		JButton btnSend = new JButton("\uC804\uC1A1");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtMessage.getText().trim().length() == 0) return;
				try {
					os.writeBytes(txtMessage.getText());
					os.write(13);
					os.write(10);
					os.flush();
					txtMessage.append("\n" + sender + ": " + txtMessage.getText());
					txtMessage.setText("");
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		panel.add(btnSend);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, BorderLayout.CENTER);
		
		txtMessages = new JTextArea();
		scrollPane_1.setViewportView(txtMessages);

		socket = s;
		this.sender = sender;
		this.receiver = receiver;
		
		try {
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new DataOutputStream(socket.getOutputStream());
			t = new OutputThread(s, txtMessages, sender, receiver);
			t.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public JTextArea getTxtMessages() {
		return this.txtMessages;
	}

}
