package inmethod.rxtx.example;


import java.io.IOException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import inmethod.rxtx.CommPortReceiver;
import inmethod.rxtx.CommPortSender;
import inmethod.rxtx.example.ProtocolImpl;

public class RS232Example {

	private SerialPort serialPort;
	
    public void disconnect() {
    	if( serialPort!=null) {
    		try {
    		   serialPort.removeEventListener();
                serialPort.close();
                serialPort=null;
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	//serialPort.close();
    	}
    }	
    
    public boolean connect(String sAppName,String portName,int iBuadRate,int iDataBits, int iStopBits, int iParity) throws NoSuchPortException,UnsupportedCommOperationException,IOException,PortInUseException {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName); // 先找出所有COM的ID

        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Port in use!");
            return false;
        } else {
            // points who owns the port and connection timeout    
             serialPort = (SerialPort) portIdentifier.open(sAppName, 2000);

            // setup connection parameters    
            serialPort.setSerialPortParams(iBuadRate, iDataBits, iStopBits, iParity);

            // setup serial port writer    
            CommPortSender.setWriterStream(serialPort.getOutputStream());
            
            // setup serial port reader    
            new CommPortReceiver(new ProtocolImpl(),serialPort.getInputStream()).start();
            return true;
        }
    }
   
    public void sendCommand(String sCmd) {
    	 CommPortSender.send(new ProtocolImpl().getMessage(sCmd+"\r\n"));
    }
    
    public static void main(String[] args) throws Exception {

        // connects to the port which name (e.g. COM1) is in the first argument    
    	RS232Example aRS232Example =   new RS232Example();
    	aRS232Example.connect("RS232Example","COM5" ,2400,SerialPort.DATABITS_7, SerialPort.STOPBITS_1, SerialPort.PARITY_EVEN);
        //new RS232Example().connect(args[0]);

        // send HELO message through serial port using protocol implementation    
         CommPortSender.send(new ProtocolImpl().getMessage("S\r\n"));
    //     aRS232Example.disconnect();
         
    }
}
