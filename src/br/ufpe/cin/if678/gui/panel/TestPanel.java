package br.ufpe.cin.if678.gui.panel;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

@SuppressWarnings("serial")
public class TestPanel extends JPanel {

	/**
	 * Cria o painel de entrada.
	 */
	public TestPanel(TakeABREAK frame) {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(400, 100, 400, 500);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(800, 600));

		JLabel label = new JLabel("TESTE");
		label.setBounds(-16, -35, 516, 596);
		panel.add(label);

		scrollPane.setViewportView(panel);
		add(scrollPane);
	}
}
