package environment;

import analysis.TypeError;

import java.util.List;
import java.util.Set;

/**
 * Created by michael on 10/25/14.
 */
public class ScopedEnvironment extends Environment {
    private GlobalEnvironment    m_globalEnvironment;
    private ScopedType           m_scope;
    private Environment<VarType> m_localVariables;

    public ScopedEnvironment(GlobalEnvironment globalEnv, ScopedType currScope)
    {
        m_scope = currScope;
        m_globalEnvironment = globalEnv;
        m_localVariables    = new Environment<VarType>();
    }

    public boolean containsLocalVariable(VarType v)
    {
        return m_localVariables.containsEntry(v.variableName());
    }

    public void addLocalVariable(VarType v)
    {
        // see if it is declared in the parameter list for the enclosing method
        if (!(m_scope instanceof MethodType))
        {
            System.err.println("Impoper call to local variables");
            System.exit(-1);
        }
        if (!m_localVariables.containsEntry(v.variableName()))
        {
            m_localVariables.addEntry(v);
        }
        else
        {
            TypeError.close("Redeclaration of " + v.variableName() + " in method " + m_scope.typeName());
        }

    }

    public VarType getVariable(String varName)
    {
        // look first in the local vars
        VarType v = m_localVariables.getEntry(varName);

        // if not found, then the globals
        if (null == v)
        {
            ClassType enclosing_class = (ClassType) m_scope.getScope();
            v = enclosing_class.getInstanceVar(varName);
        }
        return v;
    }

    public GlobalEnvironment getGlobalEnvironment()
    {
        return m_globalEnvironment;
    }

    public ScopedType getScope()
    {
        return m_scope;
    }

    public Environment getLocalVariables()
    {
        return m_localVariables;
    }
}
