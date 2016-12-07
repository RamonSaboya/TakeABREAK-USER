package br.ufpe.cin.if678.threads;

import java.awt.Cursor;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.util.Pair;

public class GroupCreationThread extends Thread {

	private TakeABREAK frame;
	private UserController controller;

	private int ID;

	public GroupCreationThread(int ID) {
		this.frame = TakeABREAK.getInstance();
		this.controller = UserController.getInstance();

		this.ID = ID;
	}

	@Override
	public void run() {
		String person1 = controller.getName(ID);
		String person2 = controller.getUser().getSecond();
		String groupName = person1 + ":!:" + person2;
		if (person1.compareTo(person2) > 0) {
			groupName = person2 + ":!:" + person1;
		}

		controller.getListener().waitGroupCreation(this);
		frame.lock();
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		synchronized ((Thread) this) {
			try {
				controller.getWriter().queueAction(UserAction.GROUP_CREATE, new Pair<Integer, String>(controller.getUser().getFirst(), groupName));
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Group group = controller.getGroup(groupName);

		synchronized (this) {
			try {
				controller.getWriter().queueAction(UserAction.GROUP_ADD_MEMBER, new Pair<String, Integer>(group.getName(), ID));
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		frame.unlock();

		frame.clearFrame();
		frame.addPanel(frame.getSidebarPanel());
		frame.addPanel(frame.getChatListPanel());
		frame.addPanel(frame.getChatPanel());

		frame.getChatListPanel().updateChatList();
	}

}
