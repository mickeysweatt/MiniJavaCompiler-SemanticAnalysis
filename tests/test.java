class Main {
    public static void main( String[] arg)
    {
        System.out.println(1);
    }
}

class B extends A{
    public int foo()
    {
        return 0;
    }
}

class A {
    public int foo()
    {
        return 1;
    }
}

class C
{
    public boolean foo(int x)
    {
        int rval;
        A a;
        a = new A() ;
        rval = a.foo();
        rval = rval + x;
        return rval < 5;
    }
}
