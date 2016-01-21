package inmethod.rxtx;

import java.io.IOException;
import java.io.InputStream;

import inmethod.rxtx.example.ProtocolImpl;

public class CommPortReceiver extends Thread {

    InputStream in;
    Protocol protocol = null;
    private CommPortReceiver(){}// 不給用
    
    public CommPortReceiver(Protocol protocol,InputStream in) {
    	this.protocol = protocol;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            int byteReceive;
            while (true) {
                // if stream is not bound in.read() method returns -1    
                while ((byteReceive = in.read()) != -1) {
                    protocol.onReceive((byte) byteReceive);
            
                  

                }
                protocol.onStreamClosed();

                // wait 10ms when stream is broken and check again    
                sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
