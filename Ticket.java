import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ticket represents a ticket printed by the TicketMachine. Being it's own
 * object, could use some own methods, too.
 *
 * @author n-c0de-r
 * @version 2021.07.21
 */

public class Ticket {
	private String lineName;
	private int ticketPrice;
	private int ticketNumber;

	/**
	 * Constructor of a Ticket.
	 * 
	 * @param name         Name of the provider.
	 * @param price        Price of a single ticket.
	 * @param ticketnumber This ticket's number.
	 */
	public Ticket(String name, int price, int number) {
		// Simulate a ticket, output to the console.
		lineName = name;
		ticketPrice = price;
		ticketNumber = number;
	}

	public String printTicketInfo() {
		String str = "##################<br>" + "# The " + lineName + " Line <br>" + "# " + ticketPrice + " cents.<br>"
				+ "##################" + "<br><br>" + "# Ticket number: " + lineName + "_" + ticketNumber + "<br><br>";

		// Set the date format.
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
		// Get the current time and add 2 hours to it, ticket is valid that long.
		str += "Valid until: " + LocalDateTime.now().plusHours(2).format(format) + "<br>" + "##################";

		return str;
	}

}
