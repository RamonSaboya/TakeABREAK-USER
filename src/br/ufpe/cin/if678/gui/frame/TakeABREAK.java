package br.ufpe.cin.if678.gui.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.gui.Test;
import br.ufpe.cin.if678.gui.panel.AuthenticationPanel;
import br.ufpe.cin.if678.gui.panel.ChatListPanel;
import br.ufpe.cin.if678.gui.panel.ChatPanel;
import br.ufpe.cin.if678.gui.panel.DisconnectedPanel;
import br.ufpe.cin.if678.gui.panel.SidebarPanel;
import br.ufpe.cin.if678.gui.panel.StartupPanel;
import br.ufpe.cin.if678.gui.panel.UserListPanel;

@SuppressWarnings("serial")
public class TakeABREAK extends JFrame {

	public static final Color BACKGROUND_COLOR = new Color(102, 255, 204);

	private static TakeABREAK INSTANCE;

	public static TakeABREAK getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TakeABREAK();
		}

		return INSTANCE;
	}

	private JPanel contentPane;

	private StartupPanel startupPanel;
	private AuthenticationPanel authenticationPanel;
	private SidebarPanel sidebarPanel;
	private UserListPanel userListPanel;
	private ChatListPanel chatListPanel;
	private ChatPanel chatPanel;

	private DisconnectedPanel disconnectedPanel;

	private HashMap<String, Test> waiting;

	/**
	 * Inicia a aplicação da GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TakeABREAK frame = getInstance();
					frame.setLocationRelativeTo(null); // Centraliza a frame na tela, de acordo com a resolução
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a frame da GUI
	 */
	private TakeABREAK() {
		// Seta as características da janela
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 725);
		setIconImage(new ImageIcon("dependencies\\32.png").getImage());
		setTitle("Take a BREAK;");

		// Inicia a contentPane (container principal)
		this.contentPane = new JPanel();
		this.contentPane.setLayout(null);
		setContentPane(contentPane);

		// Inicia as páginas do aplicativo
		this.startupPanel = new StartupPanel(this);
		this.authenticationPanel = new AuthenticationPanel(this);

		this.disconnectedPanel = new DisconnectedPanel(this);

		this.waiting = new HashMap<String, Test>();

		clearFrame();
		addPanel(startupPanel); // Define a página inicial

		// Apenas para testes
		// contentPane.add(homePanel);
		// contentPane.add(initialPanel);
		// contentPane.repaint();
		// contentPane.revalidate();
	}

	/**
	 * Remove todos os paineis em display e revalida a frame
	 */
	public void clearFrame() {
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
	}

	/**
	 * Atualiza o painel que está em display na frame
	 * 
	 * @param panel Painel que será mostrado na frame
	 */
	public void addPanel(JPanel panel) {
		// Adciona os paineis e revalida a frame
		contentPane.add(panel);
		contentPane.repaint();
		contentPane.revalidate();
	}

	public void initializePanels() {
		sidebarPanel = new SidebarPanel(this);
		userListPanel = new UserListPanel(this);
		chatListPanel = new ChatListPanel(this);
		chatPanel = new ChatPanel(this);
	}

	public void waitGroup(String groupName, Test thread) {
		waiting.put(groupName, thread);
	}

	public void notifyGroup(String groupName) {
		if (!waiting.containsKey(groupName)) {
			return;
		}

		Thread thread = waiting.get(groupName);
		synchronized (thread) {
			thread.notify();
		}
	}

	public void setDisconnected() {
		clearFrame();

		addPanel(disconnectedPanel);

		UserController.getInstance().tryReconnect();
	}

	public AuthenticationPanel getAuthenticationPanel() {
		return authenticationPanel;
	}

	public SidebarPanel getSidebarPanel() {
		return sidebarPanel;
	}

	public UserListPanel getUserListPanel() {
		return userListPanel;
	}

	public ChatListPanel getChatListPanel() {
		return chatListPanel;
	}

	public ChatPanel getChatPanel() {
		return chatPanel;
	}

}
