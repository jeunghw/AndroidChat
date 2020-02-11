package du.ac.jhw.dsctalk.ListView;

public class ChatCount {
    private String iD;
    private int Count;


    public void setiD(String iD) {
        this.iD = iD;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getCount() {
        return Integer.toString(Count);
    }

    public String getiD() {
        return iD;
    }
}