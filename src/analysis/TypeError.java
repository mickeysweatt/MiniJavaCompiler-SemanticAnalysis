package analysis;

public class TypeError {
    public static void close(String msg)
    {
        System.err.println(msg);
        System.out.println("Type error");
        System.exit(-1);
    }
}
