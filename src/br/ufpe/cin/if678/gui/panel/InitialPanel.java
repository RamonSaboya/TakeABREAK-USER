package br.ufpe.cin.if678.gui.panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class InitialPanel extends JPanel {

	private JButton signoffButton;
	private JButton contactsButton;
	private JButton groupsButton;
	private JButton mediaButton;
	private JButton homeButton;

	/**
	 * Cria o painel inicial.
	 */
	public InitialPanel() {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		// Botão de logoff
		signoffButton = new JButton("<html>Take a <br />BREAK?</html>");
		signoffButton.setBounds(0, 550, 240, 150);

		// Botão para a página de contatos
		contactsButton = new JButton("CONTACTS");
		contactsButton.setBounds(240, 550, 240, 150);

		// Botão para a página de grupos
		groupsButton = new JButton("GROUPS");
		groupsButton.setBounds(480, 550, 240, 150);

		// Botão para a páginas de mídias (downloads)
		mediaButton = new JButton("MEDIA");
		mediaButton.setBounds(720, 550, 240, 150);

		// Botão para a página inicial
		homeButton = new JButton("HOME");
		homeButton.setBounds(960, 550, 240, 150);

		// Insere todos os elementos no painel
		add(signoffButton);
		add(contactsButton);
		add(groupsButton);
		add(mediaButton);
		add(homeButton);
	}
}
