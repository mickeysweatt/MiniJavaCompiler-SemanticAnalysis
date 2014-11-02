class Main{
    public static void main( String []arg)
    {
        A b;
        A a;
    }
}

class A {
    int x;
    public int foo() {
        return x;
    }

    public boolean bar()
    {
        return false;
    }
}

class B extends A{
    boolean x;
    public boolean bar()  {
        return x;
    }
}

class foo {
    public A bar()
    {
        return new B();
    }
}