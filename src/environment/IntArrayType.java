package environment;

public class IntArrayType implements Type {

    private int m_length;

    public String typeName()
    {
        return "int["+ m_length + "]";
    }

    @Override
    public boolean subtype(Type rhs) {
        return rhs.equals(this);
    }

    IntArrayType (int len) {
        m_length = len;
    }
}
