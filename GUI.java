package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;


/**
 * GUI for the Machine, based on the former labs.
 * 
 * @author	n-c0de-r 
 * @version	14.12.2021
 */
public class GUI implements ActionListener {

	private TicketMachine machine;
	private JFrame frame;
	private JTextField display;
	private JButton cancel, discount, empty, price;
	private JPanel panel;
	private JLabel message;

	public GUI(TicketMachine ticketMachine) {
		machine = ticketMachine;
		makeFrame();
		frame.setVisible(true);
	}

	/**
	 * Set the visibility of the interface.
	 * 
	 * @param visible true if the interface is to be made visible, false otherwise.
	 */
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	private void makeFrame() {
		frame = new JFrame(machine.getProvider() + " Ticket Machine");

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setLayout(new BorderLayout(8, 8));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		display = new JTextField("" + (double) ((machine.getAmount()) /100) + "0 �");
		contentPane.add(display, BorderLayout.NORTH);
		display.setEditable(false);

		JPanel buttonPanel = new JPanel(new GridLayout(5, 3));

		price = new JButton("Show price");
		price.addActionListener(this);
		buttonPanel.add(price);
		price.setEnabled(false);
		discount = new JButton("Set discount");
		discount.addActionListener(this);
		buttonPanel.add(discount);
		discount.setEnabled(false);
		empty = new JButton("Empty machine");
		empty.addActionListener(this);
		buttonPanel.add(empty);
		empty.setEnabled(false);

		addButton(buttonPanel, "50 �");
		addButton(buttonPanel, "20 �");
		addButton(buttonPanel, "10 �");

		addButton(buttonPanel, " 5 �");
		addButton(buttonPanel, " 2 �");
		addButton(buttonPanel, " 1 �");

		addButton(buttonPanel, "50ct");
		addButton(buttonPanel, "20ct");
		addButton(buttonPanel, "10ct");

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		buttonPanel.add(cancel);
		cancel.setEnabled(true);
		addButton(buttonPanel, " ");
		addButton(buttonPanel, "Buy Ticket");

		contentPane.add(buttonPanel, BorderLayout.CENTER);
		
		panel = new JPanel();
		message = new JLabel("");
		panel.add(message);
		contentPane.add(panel, BorderLayout.SOUTH);

		frame.pack();
	}

	/**
	 * Performs an action according to the String labeling a button.
	 * 
	 * @param event	The event causing the action, a button click.
	 */
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		String upStr = "";
		String lowStr = "";
		
		if (command.equals(" ")) {
			machine.increaseService(1);
			if (machine.checkService() == 3) {
				display.setEditable(true);
				upStr = "000000";
				lowStr = "Please enter service PIN, then chose option.";
				empty.setEnabled(true);
			}
		}
		
		else {
			display.setEditable(false);
			empty.setEnabled(false);
			machine.increaseService(-machine.checkService());
			if (command.equals("Show price")) {
				lowStr = "A ticket costs " + (double) ((machine.getPrice()) /100) + "0 �";
			}
			
			else if (command.equals("Set discount")) {
//			if(checkPin()) {
//				message.setText("Enter discount.");
//			} else {
//				message.setText("Wrong PIN entered!");
//			}
			}
			
			else if (command.equals("Empty machine")) {
				if (checkPin()) {
					lowStr = "You got " + (double) ((machine.getTotal()) /100) + "0 � out";
					machine.empty();
				} else {
					lowStr = "Wrong PIN entered!";
				}
				display.setEditable(false);
				empty.setEnabled(false);
				machine.increaseService(-machine.checkService());
				upStr = "" + (double) ((machine.getAmount()) /100) + "0 �";
			}
			
			else if (command.equals("Cancel")) {
				machine.returnMoney(machine.getAmount());
				upStr = "" + (double) ((machine.getAmount()) /100) + "0 �";
				lowStr = machine.prompt();
			}
			
			else if (command.equals("Buy Ticket")) {
				 	JWindow w = new JWindow();
				  
		            // set panel
		            JPanel p = new JPanel();
		  
		            // create a label
		            JLabel l = new JLabel(machine.printTicket());
		            
		            p.add(l);
		            w.add(p);
		            
		         // setsize of window
		            w.setSize(600, 50);
		  
		            // set visibility of window
		            w.setVisible(true);
		  
		            // set location of window
		            w.setLocation(100, 100);
			}
			
			else {
				int num = 0;
				if (command.contains("�")) {
					num = (int) (Double.parseDouble(command.substring(0, command.length()-2).trim()) * 100);
				} else {
					num = Integer.parseInt(command.substring(0, command.length()-2).trim());
				}
				machine.insertMoney(num);
				upStr = "" + ((double)(machine.getAmount()) /100) + "0 �";
			}
		}
		display.setText(upStr);
		message.setText(lowStr);
	}

	/**
	 * Add a button to the button panel.
	 * 
	 * @param panel      The panel to receive the button.
	 * @param buttonText The text for the button.
	 */
	protected void addButton(Container panel, String buttonText) {
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		panel.add(button);
	}
	
	private boolean checkPin () {
		return machine.checkPin() == Integer.parseInt(display.getText());
	}
}