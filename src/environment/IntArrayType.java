package environment;

public class IntArrayType implements Type {

    private Integer m_length;

    public String typeName()
    {
        return "int["+ m_length + "]";
    }

    @Override
    public boolean subtype(Type rhs) {
        return rhs.equals(this);
    }

    public IntArrayType() {m_length = null;}

    public IntArrayType (int len) {
        m_length = len;
    }

    public void setLength(int len)
    {
        m_length = len;
    }

    public int getLength()
    {
        return m_length.intValue();
    }
}
