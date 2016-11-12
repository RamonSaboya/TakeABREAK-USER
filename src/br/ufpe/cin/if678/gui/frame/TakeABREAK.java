package br.ufpe.cin.if678.gui.frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.panel.HomePanel;
import br.ufpe.cin.if678.gui.panel.InitialPanel;
import br.ufpe.cin.if678.gui.panel.StartupPanel;
import br.ufpe.cin.if678.gui.panel.UserPanel;

@SuppressWarnings("serial")
public class TakeABREAK extends JFrame {

	public static final int BORDER_THICKNESS = 1;
	public static final Color BACKGROUND_COLOR = new Color(102, 255, 204);

	private JPanel contentPane;

	private StartupPanel startupPanel;
	private InitialPanel initialPanel;
	private UserPanel userPanel;
	private HomePanel homePanel;

	/**
	 * Inicia a aplica��o da GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TakeABREAK frame = new TakeABREAK();
					frame.setLocationRelativeTo(null); // Centraliza a frame na tela, de acordo com a resolu��o
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
	public TakeABREAK() {
		// Seta as caracter�sticas da janela
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 725);
		setIconImage(new ImageIcon("dependencies\\32.png").getImage());
		setTitle("Take a BREAK;");

		// Inicia a contentPane (container principal)
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Inicia as p�ginas do aplicativo
		startupPanel = new StartupPanel();
		initialPanel = new InitialPanel();
		userPanel = new UserPanel();
		homePanel = new HomePanel();

		setCurrent(userPanel); // Define a p�gina inicial

		// Apenas para testes
		contentPane.add(homePanel);
		contentPane.add(initialPanel);
		contentPane.repaint();
		contentPane.revalidate();
	}

	/**
	 * Atualiza o painel que est� em display na frame
	 * 
	 * @param panel Painel que ser� mostrado na frame
	 */
	public void setCurrent(JPanel panel) {
		// Remove todos os paineis em display e revalida a frame
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();

		// Adciona os paineis e revalida a frame
		contentPane.add(panel);
		contentPane.repaint();
		contentPane.revalidate();
	}

}
