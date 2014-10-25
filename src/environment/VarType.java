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

    public VarType(Type type, String name, Type scope)
    {
        super(name, scope);
        m_type = type;
    }

}
