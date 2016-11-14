package br.ufpe.cin.if678.gui.panel;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import br.ufpe.cin.if678.gui.frame.TakeABREAK;

public class ChangePassword extends JPanel {
	private JTextField txtHey;
	private JTextField textField_1;
	private JTextField textField_2;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the panel.
	 */
	public ChangePassword() {
		setBackground(Color.GREEN);
		setForeground(Color.GREEN);
		setLayout(null);
		
		JLabel lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setBounds(60, 63, 86, 14);
		add(lblCurrentPassword);
		
		txtHey = new JTextField();
		txtHey.setBounds(151, 60, 233, 20);
		add(txtHey);
		txtHey.setColumns(10);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(76, 118, 70, 14);
		add(lblNewPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(151, 115, 233, 20);
		textField_1.setColumns(10);
		add(textField_1);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(60, 173, 86, 14);
		add(lblConfirmPassword);
		
		textField_2 = new JTextField();
		textField_2.setBounds(151, 170, 233, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnOk = new JButton("Confirm");
		btnOk.setBounds(188, 225, 69, 23);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add(btnOk);

	}

}
