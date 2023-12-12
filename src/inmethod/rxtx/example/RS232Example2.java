package inmethod.rxtx.example;



import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import inmethod.rxtx.HexAndStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * This version of the TwoWaySerialComm example makes use of the 
 * SerialPortEventListener to avoid polling.
 *
 */
public class RS232Example2
{
	
	static String message="";
	SerialPort serialPort;
	 CommPortIdentifier portIdentifier;
	 CommPort commPort;
	 
	 //String COMPORT="COM18";
	 int BaudRate=2400;
	 int DATABITS=7;
	 int STOPBITS=1;
	 int PARITY=1;

	 public void setBaudRate(int baudRate) {
		BaudRate = baudRate;
	}
	 public void setDATABITS(int dATABITS) {
		DATABITS = dATABITS;
	}
	 public void setSTOPBITS(int sTOPBITS) {
		STOPBITS = sTOPBITS;
	}
	public void setPARITY(int pARITY) {
		PARITY = pARITY;
	}
	 
	 public void stop() {
		 serialPort.close();
		 serialPort=null;
		 portIdentifier=null;
		 commPort.close();
		 commPort=null;
		 System.gc();

	}
	 
	public static String getMessage() {
		return message;
	}
	
	public SerialPort getSerialPort() {
		return serialPort;
	}
	
    public RS232Example2()
    {
        super();
    }
    
    public  void  connect ( String portName ) throws Exception
    {
    	try{
         portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	    } catch (NoSuchPortException ex) {
	    	System.out.println(portName+" port ¤£¦s¦b³á!!");
	    	stop();
	        System.out.println(ex);
	     }

        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
             commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                //SerialPort serialPort = (SerialPort) commPort;
            	  serialPort = (SerialPort) commPort;
            	  
            	  System.out.println("portName="+portName);
            	  System.out.println("BaudRate="+BaudRate);
            	  System.out.println("DATABITS="+DATABITS);
            	  System.out.println("STOPBITS="+STOPBITS);
            	  System.out.println("PARITY="+PARITY);
            	  
            	  
//            	  serialPort.setSerialPortParams(BaudRate,DATABITS,STOPBITS,PARITY);
            	  
                serialPort.setSerialPortParams(2400,SerialPort.DATABITS_7,SerialPort.STOPBITS_1,SerialPort.PARITY_EVEN); //AC-9000
//                serialPort.setSerialPortParams(4800,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE); EX-2100

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();      
                (new Thread(new SerialWriter(out))).start();
                serialPort.addEventListener(new SerialReader(in));
                out.write("S\r\n".getBytes());
                serialPort.notifyOnDataAvailable(true);

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;
          
            try
            {
                int len = 0;
                while ( ( data = in.read()) > -1 )
                {
           
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                
                System.out.println("~serialEvent~"+new Date());
                message=new String(buffer,0,len);
                
               // System.out.print(new String(buffer,0,len));
                
                System.out.print( HexAndStringConverter.convertHexByteToHexString(buffer));
            }   		
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }

    }

    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                	//String mValue = "S\r\n";
                    //this.out.write(mValue.getBytes());
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }            
        }
    }
    

    
    public static void main ( String[] args )
    {
        try
        {
            (new RS232Example2()).connect("COM5");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
