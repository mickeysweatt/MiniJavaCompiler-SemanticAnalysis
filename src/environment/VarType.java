package environment;

/**
 * Created by admin on 10/24/14.
 */
public class VarType implements Type {
    private Type   m_type;
    private String m_name;

    public String typeName()
    {
        return m_type.typeName();
    }

    public String variableName()
    {
        return m_name;
    }

    public VarType(Type type, String name)
    {
        m_name = name;
        m_type = type;
    }

}
