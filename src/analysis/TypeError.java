package analysis;

public class TypeError {
    public static void close(String msg)
    {
        System.out.println("Type error");
        //System.err.println(": + msg + \n");
        int x = 1/0;
        System.exit(-1);

    }
}
