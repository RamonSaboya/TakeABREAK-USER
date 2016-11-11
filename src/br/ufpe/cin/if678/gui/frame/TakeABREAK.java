package br.ufpe.cin.if678.gui.frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.panel.InitialPanel;
import br.ufpe.cin.if678.gui.panel.StartupPanel;

@SuppressWarnings("serial")
public class TakeABREAK extends JFrame {

	public static final Color BACKGROUND_COLOR = new Color(102, 255, 204);

	private JPanel contentPane;

	private StartupPanel startupPanel;
	private InitialPanel initialPanel;

	/**
	 * Inicia a aplicação da GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TakeABREAK frame = new TakeABREAK();
					frame.setLocationRelativeTo(null);
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1200, 725);
		setIconImage(new ImageIcon("dependencies\\32.png").getImage());
		setTitle("Take a BREAK;");

		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		startupPanel = new StartupPanel();
		initialPanel = new InitialPanel();

		setCurrent(initialPanel);
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
