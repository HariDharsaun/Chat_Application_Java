import java.util.*;
import java.sql.*;

public class dbHandle {
	private static final String url = "jdbc:mysql://localhost:3306/chat_app";
    private static final String sql_uname = "root";
    private static final String sql_pd = "root";

    // Register user
    public boolean registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, sql_uname, sql_pd)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login user
    public user loginUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(url, sql_uname, sql_pd)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new user(rs.getString("username"), rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Invalid credentials
    }

    // Send a message
    public void sendMessage(String fromUser, String toUser, String content) {
        try (Connection conn = DriverManager.getConnection(url, sql_uname, sql_pd)) {
            String query = "INSERT INTO messages (from_user, to_user, content) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, fromUser);
                stmt.setString(2, toUser);
                stmt.setString(3, content);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get messages for a user
    public List<message> getMessagesForUser(String username) {
        List<message> userMessages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, sql_uname, sql_pd)) {
            String query = "SELECT * FROM messages WHERE to_user = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String fromUser = rs.getString("from_user");
                    String toUser = rs.getString("to_user");
                    String content = rs.getString("content");
                    userMessages.add(new message(fromUser, toUser, content));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userMessages;
    }
}