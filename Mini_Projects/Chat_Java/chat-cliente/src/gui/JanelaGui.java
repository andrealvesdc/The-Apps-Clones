package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public abstract class JanelaGui{
	
	private String nomeAplicacao = "AndreAlves - Chat";
	private JFrame chatframe = new JFrame(nomeAplicacao);
	private JButton enviarMensagemButon;
	private JTextField mensagemTextField;
	
	private JTextArea chatArea;
	private JTextField nomeTextField;
	private JFrame nomeUsuarioFrame;
	private String username;
	
	public JanelaGui() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				solicitarNomeUsuario();
				
			}
		});
	}
	
	protected void solicitarNomeUsuario() {
		chatframe.setVisible(false);
		nomeUsuarioFrame = new JFrame(nomeAplicacao);
		nomeUsuarioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nomeTextField = new JTextField(9);
		
		JLabel escolherNomeLabel = new JLabel("Informe seu nome");
		JButton acessarServidorButton = new JButton("Entrar no chat");
		acessarServidorButton.addActionListener(new AcessarServidorButtonListener());
		JPanel nomeUsuarioPainel = new JPanel(new GridBagLayout());
		
		GridBagConstraints preRight = new GridBagConstraints();
		preRight.insets = new Insets(0,0,0,20);
		preRight.anchor = GridBagConstraints.EAST;
		GridBagConstraints preleft = new GridBagConstraints();
		
		preleft.anchor = GridBagConstraints.WEST;
		preleft.insets = new Insets(0, 10, 0, 10);
		preRight.fill = GridBagConstraints.HORIZONTAL;
		preRight.gridwidth = GridBagConstraints.REMAINDER;
		
		nomeUsuarioPainel.add(escolherNomeLabel, preleft);
		nomeUsuarioPainel.add(nomeTextField, preRight);
		nomeUsuarioFrame.add(BorderLayout.CENTER, nomeUsuarioPainel);
		nomeUsuarioFrame.add(BorderLayout.SOUTH, acessarServidorButton);
		nomeUsuarioFrame.setSize(300, 300);
		nomeUsuarioFrame.setVisible(true);
	}
	
	class AcessarServidorButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			username = nomeTextField.getText();
			
			if (username.length() < 1) {
				JOptionPane.showMessageDialog(null, "Inform um nome de usuário.", "Atenção", JOptionPane.WARNING_MESSAGE);
				
			}else {
				if(conectar()) {
					nomeUsuarioFrame.setVisible(false);
					mostrarJanelaChat();
				}else {
					JOptionPane.showMessageDialog(null, "Erro conectando ao servidor.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}
	
	public void mostrarJanelaChat() {
		JPanel mainPainel = new JPanel();
		mainPainel.setLayout(new BorderLayout());
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.GRAY);
		southPanel.setLayout(new GridBagLayout());
		
		mensagemTextField = new JTextField(30);
		mensagemTextField.addActionListener(new EnviarMensagemListener());
		
		enviarMensagemButon = new JButton("Enviar");
		enviarMensagemButon.addActionListener(new EnviarMensagemListener());
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setFont(new Font("Serif", Font.PLAIN, 15));
		chatArea.setLineWrap(true);
		
		mainPainel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
		
		GridBagConstraints left = new GridBagConstraints();
		left.anchor = GridBagConstraints.LINE_START;
		left.fill = GridBagConstraints.HORIZONTAL;
		left.weightx = 512.0D;
		left.weighty = 1.0D;
		
		GridBagConstraints right = new GridBagConstraints();
		right.insets = new Insets(0, 10, 0, 0);
		right.anchor = GridBagConstraints.LINE_END;
		right.fill = GridBagConstraints.NONE;
		right.weightx = 1.0D;
		right.weighty = 1.0D;
		
		southPanel.add(mensagemTextField, left);
		southPanel.add(enviarMensagemButon, right);
		mainPainel.add(BorderLayout.SOUTH, southPanel);
		
		chatframe.add(mainPainel);
		chatframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatframe.setSize(470, 300);
		chatframe.setVisible(true);
		mensagemTextField.requestFocusInWindow();
		
	}
	
	class EnviarMensagemListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			if (mensagemTextField.getText().length() < 1) {
				// depois faço
			}else {
				String msg = "<" + username + ">: " + mensagemTextField.getText() + "\n";
				sendMessage(msg);
				
			}
			
			mensagemTextField.requestFocusInWindow();
		}
	}
	
	protected String getUsername() {
		return this.username;
	}
	
	public void adicionarMensagem(String mensagem) {
		if (chatArea != null) {
			chatArea.append(mensagem);
		}
	}
	
	protected abstract boolean conectar();
	protected abstract void sendMessage(String mensagem);
}
