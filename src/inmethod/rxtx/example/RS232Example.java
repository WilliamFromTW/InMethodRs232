package inmethod.rxtx.example;

import java.io.IOException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import inmethod.rxtx.CommPortReceiver;
import inmethod.rxtx.CommPortSender;

public class RS232Example {

    public void connect(String portName) throws NoSuchPortException,UnsupportedCommOperationException,IOException,PortInUseException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName); // 先找出所有COM的ID

        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Port in use!");
        } else {
            // points who owns the port and connection timeout    
            SerialPort serialPort = (SerialPort) portIdentifier.open("RS232Example", 2000);

            // setup connection parameters    
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            // setup serial port writer    
            CommPortSender.setWriterStream(serialPort.getOutputStream());

            // setup serial port reader    
            new CommPortReceiver(new ProtocolImpl(),serialPort.getInputStream()).start();
        }
    }

    public static void main(String[] args) throws Exception {

        // connects to the port which name (e.g. COM1) is in the first argument    
        new RS232Example().connect("COM3");
        //new RS232Example().connect(args[0]);

        // send HELO message through serial port using protocol implementation    
        // CommPortSender.send(new ProtocolImpl().getMessage("CM0\r\n"));
    }
}
