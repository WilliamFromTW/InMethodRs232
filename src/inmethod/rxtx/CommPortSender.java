package inmethod.rxtx;

import java.io.IOException;
import java.io.OutputStream;

public class CommPortSender {

    static OutputStream out;

    public static void setWriterStream(OutputStream out) {
        CommPortSender.out = out;
    }

    public static void send(byte[] bytes) {
        try {
            System.out.println("SENDING: " + new String(bytes, 0, bytes.length));

            // sending through serial port is simply writing into OutputStream    
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
