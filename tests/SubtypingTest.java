class Main {
	public static void main(String[] args) {
		A a;
		B b;
        Foo f;
        f = new Foo();
		a = new A();
		b = new B();
	}
}

class Foo {
	public A foo(A a)
	{
		return a;
	}
}

class A
{
	int x;
}

class B extends A
{
	int y;
}