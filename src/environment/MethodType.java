package environment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by admin on 10/23/14.
 */
public class MethodType extends ScopedType {
    private Type                m_returnType;
    private LinkedList<VarType> m_parameters;

    public String typeName()
    {
        return "method";
    }

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


    public LinkedList<VarType> getParameterList()
    {
        return m_parameters;
    }
}
