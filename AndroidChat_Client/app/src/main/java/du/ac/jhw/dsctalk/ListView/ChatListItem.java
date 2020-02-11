package du.ac.jhw.dsctalk.ListView;

public class ChatListItem {
    private String iD;
    private String message;

    public void SetID(String iD) {
        this.iD = iD;
    }

    public void SetMessage(String message) {
        this.message = message;
    }

    public String GetID() {
        return iD;
    }

    public String GetMessage() {
        return message;
    }
}
