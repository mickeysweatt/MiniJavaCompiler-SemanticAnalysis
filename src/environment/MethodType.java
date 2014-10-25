package environment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 10/23/14.
 */
public class MethodType implements Type {
    private String       m_methodName;
    private Type         m_returnType;
    private Set<VarType> m_parameters;

    public String typeName()
    {
        return "method";
    }

    public  MethodType(String name, Type returnType, Set<VarType> parameters)
    {
        m_methodName = name;
        m_returnType = returnType;
        m_parameters = parameters;
    }

    public void addParameter(VarType parameter)
    {
        if (m_parameters == null)
        {
            m_parameters = new HashSet<VarType>();
        }
        m_parameters.add(parameter);
    }

    public boolean containsParameter(VarType parameter)
    {
        return (null != m_parameters) && (m_parameters.contains(parameter));
    }

    public String getName()
    {
        return m_methodName;
    }
}
