package br.ufpe.cin.if678.gui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.util.Map;

import br.ufpe.cin.if678.UserController;
import br.ufpe.cin.if678.business.Group;
import br.ufpe.cin.if678.communication.UserAction;
import br.ufpe.cin.if678.gui.frame.TakeABREAK;
import br.ufpe.cin.if678.util.Pair;

public class Test extends Thread implements ActionListener {

	private TakeABREAK frame;
	private Map.Entry<String, InetSocketAddress> user;

	public Test(TakeABREAK frame, Map.Entry<String, InetSocketAddress> user) {
		this.frame = frame;
		this.user = user;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent event) {
		UserController controller = UserController.getInstance();

		String person1 = controller.getName(user.getValue());
		String person2 = controller.getName(controller.getUser());
		String groupName = person1 + ":!:" + person2;
		if (person1.compareTo(person2) > 0) {
			groupName = person2 + ":!:" + person1;
		}

		controller.getWriter().queueAction(UserAction.GROUP_CREATE, new Pair<InetSocketAddress, String>(controller.getUser(), groupName));

		frame.waitGroup(groupName, this);
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		Group group = controller.getGroup(groupName);
		controller.getWriter().queueAction(UserAction.GROUP_ADD_MEMBER, new Pair<String, InetSocketAddress>(group.getName(), user.getValue()));

		frame.clearFrame();
		frame.addPanel(frame.getSidebarPanel());
		frame.addPanel(frame.getChatListPanel());
		frame.addPanel(frame.getChatPanel());
	}

}
