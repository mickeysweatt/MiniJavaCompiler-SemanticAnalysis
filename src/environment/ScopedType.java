package environment;

/**
 * Created by michael on 10/25/14.
 */
public class ScopedType implements Type {

    private Type   m_scope;
    private String m_name;

    protected ScopedType(String name, Type scope)
    {
        m_name = name;
        m_scope = scope;
    }

    public boolean subtype(Type rhs)
    {
        return false;
    }

    public Type getScope()
    {
        return m_scope;
    }

    public void setScope(Type scope)
    {
        m_scope = scope;
    }

    public String typeName()
    {
        return m_name;
    }

    public String toString()
    {
        return m_name;
    }
}
