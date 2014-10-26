package environment;

public class PrimitiveType implements Type
{
    public static PrimitiveTypeImpl INT_TYPE  = new PrimitiveTypeImpl(PrimitiveTypeImpl.e_TYPE.INT);
    public static PrimitiveTypeImpl BOOL_TYPE = new PrimitiveTypeImpl(PrimitiveTypeImpl.e_TYPE.BOOL);

    public String typeName()
    {
        return null;
    }

    public boolean subtype(Type rhs)
    {
        return rhs.equals(this);
    }

}

class PrimitiveTypeImpl implements Type {

    public enum e_TYPE {
        INT, BOOL;
    }

    e_TYPE m_type;

    PrimitiveTypeImpl(e_TYPE t)
    {
        m_type = t;
    }

    public String typeName()
    {
        String rval = null;
        if (m_type == e_TYPE.INT)
        {
            rval =  "int";
        }
        else if (m_type == e_TYPE.BOOL)
        {
            rval = "boolean";
        }
        return rval;
    }

    public boolean subtype(Type rhs)
    {
        return rhs.equals(this);
    }
}


