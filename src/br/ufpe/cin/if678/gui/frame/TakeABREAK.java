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
	 * Inicia a aplicação da GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TakeABREAK frame = new TakeABREAK();
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
	public TakeABREAK() {
		// Seta as características da janela
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 725);
		setIconImage(new ImageIcon("dependencies\\32.png").getImage());
		setTitle("Take a BREAK;");

		// Inicia a contentPane (container principal) lula
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Inicia as páginas do aplicativo
		startupPanel = new StartupPanel();
		initialPanel = new InitialPanel();
		userPanel = new UserPanel();
		homePanel = new HomePanel();

		setCurrent(userPanel); // Define a página inicial

		// Apenas para testes
		contentPane.add(homePanel);
		contentPane.add(initialPanel);
		contentPane.repaint();
		contentPane.revalidate();
	}

	/**
	 * Atualiza o painel que está em display na frame
	 * 
	 * @param panel Painel que será mostrado na frame
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
