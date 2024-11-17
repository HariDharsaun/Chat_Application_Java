import java.util.*;

public class main {
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
        boolean flag = true;
        user loggedIn = null;
        dbHandle dbHandle = new dbHandle();

        while (flag) {
            System.out.println("1. Register\n"+"2. Login\n"+"3. Send Message\n"+"4. View All Messages\n"+"5.View Recent Message\n"+"6. Logout\n"+"7. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    // Register user
                    System.out.println("Enter username:");
                    String uname = sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.nextLine();
                    System.out.println("Confirm password:");
                    String confirmPassword = sc.nextLine();

                    if (!password.equals(confirmPassword)) {
                        while(true)
                        {
                        	System.out.println("Passwords do not match. Try again.");
                        	confirmPassword = sc.nextLine();
                        	if(password.equals(confirmPassword))
                        	{
                        		break;
                        	}
                        }
                    }

                    if (dbHandle.registerUser(uname, password)) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("User already exists.");
                    }
                    break;

                case 2:
                    // Login user
                    System.out.println("Enter username:");
                    String loginUsername = sc.nextLine();
                    System.out.println("Enter password:");
                    String loginPassword = sc.nextLine();

                    loggedIn = dbHandle.loginUser(loginUsername, loginPassword);

                    if (loggedIn != null) {
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;

                case 3:
                    // Send message
                    if (loggedIn == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    System.out.println("Enter recipient username:");
                    String recipient = sc.nextLine();
                    System.out.println("Enter message content:");
                    String messageContent = sc.nextLine();

                    dbHandle.sendMessage(loggedIn.username, recipient, messageContent);
                    System.out.println("Message sent!");
                    break;

                case 4:
                    // View messages
                    if (loggedIn == null) {
                        System.out.println("Please login first.");
                        break;
                    }

                    List<message> messages = dbHandle.getMessagesForUser(loggedIn.username);
                    if (messages.isEmpty()) {
                        System.out.println("No messages.");
                    } else {
                        System.out.println("Messages for " + loggedIn.username + ":");
                        for (message message : messages) {
                            System.out.println("From: " + message.fromUser + " - " + message.content);
                            System.out.println(message.date_time);
                        }
                    }
                    break;
                case 5:
                	if(loggedIn == null)
                	{
                		System.out.println("Please login first.");
                        break;
                	}
                	dbHandle.getRecentMessage(loggedIn.username);
                	break;

                case 6:
                    // Logout
                    loggedIn = null;
                    System.out.println("Log out successfully.");
                    break;

                case 7:
                    // Exit
                    System.out.println("Exiting.....");
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }

        sc.close();

	}
}
