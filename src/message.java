import java.sql.*;
public class message {
	String fromUser;
    String toUser;
    String content;
    Timestamp date_time;

   public message(String fromUser, String toUser, String content,Timestamp datetime) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.date_time = datetime;
    }
	
//	public String getfromUser()
//	{
//		return from_user;
//	}
//	public String gettoUser()
//	{
//		return to_user;
//	}
//	public String getContent()
//	{
//		return content;
//	}
}
