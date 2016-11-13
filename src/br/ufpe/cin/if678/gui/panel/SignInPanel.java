package br.ufpe.cin.if678.gui.panel;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;

public class SignInPanel extends JPanel {
	private JTextField userTextField;
	private JTextField passwordTextField;
	/**
	 * Create the SignIn panel.
	 */
	public SignInPanel() {
		super();

		// Seta as caracteristicas do painel
		setBounds(0, 0, 1200, 700);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);
		
		// TORNAR ATRIBUTOS PRIVADOS
		userTextField = new JTextField();
		userTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		userTextField.setBounds(350, 195, 500, 33);
		add(userTextField);
		userTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("USERNAME:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(150, 180, 150, 67);
		add(lblNewLabel);
		
		passwordTextField = new JTextField();
		passwordTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(350, 285, 500, 33);
		add(passwordTextField);
		
		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(150, 268, 150, 67);
		add(lblPassword);
		
		JButton btnNewButton = new JButton("Forgot Something?");
		
		btnNewButton.setBounds(150, 397, 194, 23);
		add(btnNewButton);
		
		JList list = new JList();
		list.setBounds(85, 496, 1, 1);
		add(list);
		
		JButton btnNewButton_1 = new JButton("TAKE A BREAK!");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setBounds(614, 351, 236, 69);
		add(btnNewButton_1);

	}
}
