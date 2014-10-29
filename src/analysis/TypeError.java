package analysis;

public class TypeError {
    public static void close(String msg)
    {
        System.out.print("Type error: ");
        System.err.println(msg);
        System.exit(-1);
    }
}
