class Factorial{
    public static void main(String[] a){
        System.out.println(new Fac().ComputeFac(10));
    }

}

class Fac {

    public int ComputeFac(int num) {
        int num_aux;
        if (num < 1)
            num_aux = 1;
        else
            num_aux = num * (this.ComputeFac(num - 1));
        return num_aux;
    }
}

class Fib {
    int [] fib;

    public int init()
    {
        fib = new int [2];
        fib[0] = 1;
        fib[1] = 1;
        return 0;
    }

    public int ComputFib(int n)
    {
        int array_len;
        int [] temp;
        int i;
        i = 0;
        array_len = fib.length;
        if (array_len < n)
        {
            temp = new int [n];
            while(i< array_len)
            {
                temp[i] = fib[i];
                i = i + 1;
            }
        }
        else
        {}
        i = array_len;
        while ((i < n) && !(n < (i - 1))) {
            fib[i] = (fib[(i-1)]) + (fib[(i-2)]);
            i = i + 1;
        }
        return fib[n];
    }
}

