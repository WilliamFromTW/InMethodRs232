How To Compile
**Support Microsoft  windows Only

1. j2se version 17(only)

2. gradle 8.11 or above
   gradle jar => generate jar file
   
3.  use nrjavaserial-5.2.1.jar  (fork from RXTXcomm.jar)    
https://github.com/NeuronRobotics/nrjavaserial/releases    

How To Execute
example:
java -Djava.library.path=. -cp .;./InMethodRs232-1.1_20231212.jar;./nrjavaserial-5.2.1.jar inmethod.rxtx.example.RS232Example COM5

     
2. COM3 can be modified suitable serial com port
     

--
Regards,
2023/12/12
William Chen