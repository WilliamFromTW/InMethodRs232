package inmethod.rxtx;



import gnu.io.CommPortIdentifier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ListAvailablePorts {

    public List<CommPortIdentifier> list() {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        List aList = new ArrayList<String>();
        
        while (ports.hasMoreElements()) {
            CommPortIdentifier cpIdentifier = (CommPortIdentifier) ports.nextElement();
            System.out.println(cpIdentifier.getName());
            aList .add(cpIdentifier);
        }
        return aList;
    }

    public static void main(String[] args) {
        new ListAvailablePorts().list();
    }
}
