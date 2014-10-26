package environment;

/**
 * Created by admin on 10/24/14.
 */
public class VarType extends ScopedType {
    private Type   m_type;

    public String typeName()
    {
        return m_type.typeName();
    }

    public String variableName()
    {
        return toString();
    }

    public Type  variableType() { return m_type;}

    public VarType(Type type, String name, Type scope)
    {
        super(name, scope);
        m_type = type;
    }

    public boolean subset(Type rhs)
    {
        return m_type.subtype(rhs);
    }


}
