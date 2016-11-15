package br.ufpe.cin.if678.gui.panel;

import javax.swing.JPanel;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfilePanel extends JPanel {
	private JLabel userName;
	private JLabel fullName;
	private JLabel eMail;
	private JLabel birthDate;
	private JLabel cellNumber;
	private JButton editInfo;
	private JButton changePassword;
	
	/**
	 * Create the panel.
	 */
	public ProfilePanel() {
		super();

		// Seta as caracter�sticas do painel
		setBounds(0, 0, 1200, 550);
		setBackground(TakeABREAK.BACKGROUND_COLOR);
		setLayout(null);
		
		userName = new JLabel("Buzze");
		userName.setFont(new Font("Tahoma", Font.PLAIN, 36));
		userName.setBounds(10, 11, 300, 45);
		add(userName);

		fullName = new JLabel("Douglas Soares Lins");
		fullName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		fullName.setBounds(10, 61, 300, 60);
		add(fullName);
		
		eMail = new JLabel("dsl@cin.ufpe.br");
		eMail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		eMail.setBounds(93, 118, 127, 60);
		add(eMail);
		
		birthDate = new JLabel("05/01/1996");
		birthDate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		birthDate.setBounds(150, 172, 250, 60);
		add(birthDate);
		
		cellNumber = new JLabel("81 8825-4456");
		cellNumber.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cellNumber.setBounds(120, 224, 250, 60);
		add(cellNumber);
		
		editInfo = new JButton("Edit Info");
		editInfo.setBounds(181, 329, 150, 30);
		add(editInfo);
		
		changePassword = new JButton("Change Password");		
		changePassword.setBounds(10, 329, 150, 30);
		add(changePassword);
		
		JLabel lblNewLabel = new JLabel("E-Mail:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 132, 100, 30);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Date of Birth:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 186, 130, 30);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cellphone:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(10, 238, 100, 30);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("INSERT PHOTO HERE");
		lblNewLabel_3.setBounds(391, 61, 183, 194);
		add(lblNewLabel_3);

	}
}
