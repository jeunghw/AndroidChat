package du.ac.jhw.dsctalk.message.send;

import java.io.DataOutputStream;
import java.io.IOException;

import du.ac.jhw.dsctalk.message.SendMessage;

public class UnkowneMessage extends SendMessage {

    private String text;
    public UnkowneMessage(String text) {
        super(0, text.getBytes().length);
        this.text = text;
    }

    @Override
    public void sending(DataOutputStream outputStream) throws IOException {
        sendingHeader(outputStream);
        outputStream.write(text.getBytes());
    }
}
