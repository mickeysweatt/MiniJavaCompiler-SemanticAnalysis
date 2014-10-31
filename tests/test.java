class Main{
    public static void main( String []arg)
    {
        A b;
        A a;
        b = new B();
        a = new A();
        if((b.bar()) && (a.bar()))
            System.out.println(1);
        else
        {

        }
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

    public int foo1()
    {
        return this.foo();
    }

}