package FIR;
import FIR.SEC.Second;
import FIR.SEC.THI.Third;
public class First{
    public static void main(String args[])
    {  
        System.out.println("This is First Class");
        Second ob = new Second();
        Third ob3 = new Third();
        ob.funS();
        ob3.fun3();
        ob.fun3();
    }    
}
