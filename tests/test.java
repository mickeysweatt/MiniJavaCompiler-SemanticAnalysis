class Main {
    public static void main(String [] args)
    {

    }
}

class A {
    int a;
    B b;

    public int f0() {return a;}

    public int f1(int x, boolean b, int y)
    {
        return x + a;
    }

    public int f2(int y)
    {
        return a + y;
    }
}

class B {
    int b;
}