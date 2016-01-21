How To Compile
**Hhis library is support Microsfot windows

1. j2se version 8 or above

2. gradle 2.8 or above
   gradle jar => generate jar file

How To Execute
example:
java -Djava.library.path=. -cp ./InMethodRs232-1.0.jar;./RXTXcomm.jar inmethod.example..RS232Example COM3

1.
   -Djava.library.path=<two dll file>
   there two dll file 
   in directory libs\
     rxtxParallel.dll
     rxtxSerial.dll
2. COM3 can be modified sutiable serial com port
     

--
Regards,
2016/01/21
William Chen