package inmethod.rxtx;

public interface Protocol {

    // protocol manager handles each received byte    
    void onReceive(byte byteReceive);

    // protocol manager handles broken stream    
    void onStreamClosed();
}
