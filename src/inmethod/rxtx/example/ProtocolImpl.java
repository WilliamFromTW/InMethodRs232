package inmethod.rxtx.example;

import inmethod.rxtx.CommPortSender;
import inmethod.rxtx.Protocol;

public class ProtocolImpl implements Protocol {

    byte[] buffer = new byte[1024];
    int tail = 0;

    @Override
    public void onReceive(byte byteReceive) {
        if (byteReceive == '\n') {
            this.onMessage();
        } else {
            buffer[tail] = byteReceive;
            tail++;
            System.out.println("byteReceive = " + byteReceive);
        }
    }

    @Override
    public void onStreamClosed() {
        this.onMessage();
    }

    /*  
     * When message is recognized onMessage is invoked   
     */
    private void onMessage() {
        if (tail != 0) {
            String message = getMessage(buffer, tail);
            System.out.println("RECEIVED MESSAGE: " + message);

            if( message.indexOf("P3:")!=-1){
                CommPortSender.send(getMessage("C"));
                CommPortSender.send(getMessage("M"));
                CommPortSender.send(getMessage("0\r\n"));
            }  
            tail = 0;
        }
    }

    // helper methods     
    public byte[] getMessage(String message) {
        return (message).getBytes();
    }

    public String getMessage(byte[] buffer, int len) {
        return new String(buffer, 0, tail);
    }
}
