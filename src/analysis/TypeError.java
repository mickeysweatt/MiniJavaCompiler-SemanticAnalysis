package analysis;

public class TypeError {
    public static void close(String msg)
    {
        System.err.println(msg);
        System.exit(-1);
    }
}
