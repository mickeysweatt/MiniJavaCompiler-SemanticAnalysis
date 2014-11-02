package environment;

import java.util.HashSet;
import java.util.LinkedList;

/**
 Author: Mickey Sweatt
 */
public class MethodType extends ScopedType {
    private Type                m_returnType;
    private LinkedList<VarType> m_parameters;
    private HashSet<VarType> m_localVars;

    public  MethodType(String name, Type returnType, ClassType scope, LinkedList<VarType> parameters)
    {
        super(name, scope);
        m_returnType = returnType;
        m_parameters = parameters;
    }

    public void addParameter(VarType parameter)
    {
        addLocalVar(parameter);
        if (m_parameters == null)
        {
            m_parameters = new LinkedList<VarType>();
        }
        m_parameters.add(parameter);

    }

    public void addLocalVar(VarType localVar)
    {
        if (null == m_localVars)
        {
            m_localVars = new HashSet<VarType>();
        }
        m_localVars.add(localVar);
    }

    public HashSet<VarType> getLocalVars()
    {
        return m_localVars;
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

    public String typeName() {
        return super.typeName();
    }

    public String toString()
    {
        String rval = "";
        if (getScope() != null)
        {
            rval += getScope().typeName() + "::";
        }
        rval += super.typeName();
        return rval;
    }

    public LinkedList<VarType> getParameterList()
    {
        if (null == m_parameters)
        {
            m_parameters = new LinkedList<VarType>();
        }
        return m_parameters;
    }
}
