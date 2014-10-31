package analysis;

public class TypeError {
    public static void close(String msg)
    {
        System.out.println("Type error: " + msg + "\n");
        int x = 1/0;
    }
}
