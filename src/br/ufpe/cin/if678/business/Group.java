package br.ufpe.cin.if678.business;

import java.util.ArrayList;
import java.util.Date;

public class Group {

	private int ID;
	private String name;
	private ArrayList<User> members;
	private ArrayList<User> admin;
	private int amount;
	private Date birthday;

	public Group(int ID, String name, User user, Date date) {
		this.ID = ID;
		this.name = name;
		this.members.add(user);
		this.admin.add(user);
		this.amount = 1;
		this.birthday = date;
	}

	public boolean add(User checkUser, User user) {
		if (this.admin.contains(checkUser)) {
			this.members.add(user);
			this.amount++;
			return true;
		} else {
			return false;
		}

	}

	public boolean remove(User checkUser, User user) {
		if (this.admin.contains(checkUser)) {
			this.members.remove(user);
			this.amount--;
			return true;
		} else {
			return false;
		}

	}

	public boolean makeAdmin(User checkUser, User user) {
		if (this.admin.contains(checkUser)) {
			this.admin.add(user);
			return true;
		} else {
			return false;
		}

	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public ArrayList getMembers() {
		return members;
	}

	public ArrayList getAdmin() {
		return admin;
	}

	public int getAmount() {
		return amount;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setID(int ID) {
		ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMembers(ArrayList members) {
		this.members = members;
	}

	public void setAdmin(ArrayList admin) {
		this.admin = admin;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}
