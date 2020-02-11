package du.dsc.messenger.server.message;

public abstract class BaseMessage {
	
	protected int MessageType;
	protected int MessageSize;
	
	public static final int ClientIntSize = 4;
	
	public static final int userIDSize = 50;
	
	public static final int Base = 1000;
	public static final int LoginType = Base + 1;
	public static final int GetUsersList = Base + 2;
	public static final int chat = Base + 3;
	
	public BaseMessage(int MessageType , int MessageSize)
	{
		this.MessageType = MessageType;
		this.MessageSize = MessageSize;
	}
}
