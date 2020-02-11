package du.ac.jhw.dsctalk.message;

public abstract class BaseMessage {

    protected int messageType;
    protected int messageSize;

    public static final int userIDSize = 50;

    public static final int base = 1000;
    public static final int loginType = base + 1;
    public static final int getUsersList = base + 2;
    public static final int chat = base + 3;

    public BaseMessage(int messageType, int messageSize){
        this.messageType = messageType;
        this.messageSize = messageSize;
    }

    public int getMessageType() { return messageType; }
}

