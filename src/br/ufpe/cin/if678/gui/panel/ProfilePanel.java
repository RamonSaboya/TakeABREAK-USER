package br.ufpe.cin.if678.gui.panel;

import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfilePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ProfilePanel() {
		super();

		// Seta as características do painel
		setBounds(0, 0, 1200, 550);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);
		
		JLabel labelUsername = new JLabel("Buzze");
		labelUsername.setFont(new Font("Tahoma", Font.PLAIN, 36));
		labelUsername.setBounds(560, 60, 300, 45);
		add(labelUsername);

		JLabel labelFullName = new JLabel("Douglas Soares Lins");
		labelFullName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		labelFullName.setBounds(560, 110, 300, 60);
		add(labelFullName);
		
		JLabel labelEmail = new JLabel("dsl@cin.ufpe.br");
		labelEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelEmail.setBounds(350, 210, 250, 60);
		add(labelEmail);
		
		JLabel labelBirthDate = new JLabel("05/01/1996");
		labelBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelBirthDate.setBounds(350, 265, 250, 60);
		add(labelBirthDate);
		
		JLabel labelNumber = new JLabel("81 8825-4456");
		labelNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		labelNumber.setBounds(350, 320, 250, 60);
		add(labelNumber);
		
		JButton btnEditInfo = new JButton("Edit Info");
		btnEditInfo.setBounds(640, 230, 150, 30);
		add(btnEditInfo);
		
		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnChangePassword.setBounds(640, 271, 150, 30);
		add(btnChangePassword);

	}
}
