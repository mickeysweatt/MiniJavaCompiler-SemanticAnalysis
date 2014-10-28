package environment;

import java.util.LinkedList;

/**
 Author: Mickey Sweatt
 */
public class MethodType extends ScopedType {
    private Type                m_returnType;
    private LinkedList<VarType> m_parameters;

    public  MethodType(String name, Type returnType, ClassType scope, LinkedList<VarType> parameters)
    {
        super(name, scope);
        m_returnType = returnType;
        m_parameters = parameters;
    }

    public void addParameter(VarType parameter)
    {
        if (m_parameters == null)
        {
            m_parameters = new LinkedList<VarType>();
        }
        m_parameters.add(parameter);
    }

    public boolean containsParameter(VarType parameter)
    {
        return (null != m_parameters) && (m_parameters.contains(parameter));
    }

    public Type getReturnType()
    {
        return m_returnType;
    }

    public String methodName() { return typeName(); }


    public LinkedList<VarType> getParameterList()
    {
        if (null == m_parameters)
        {
            m_parameters = new LinkedList<VarType>();
        }
        return m_parameters;
    }
}
