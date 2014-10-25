package environment;

/**
 * Created by michael on 10/25/14.
 */
public class ScopedEnvironment {
    GlobalEnvironment    m_globalEnvironment;
    ScopedType                 m_scope;
    Environment<VarType> m_localVariables;

    ScopedEnvironment(GlobalEnvironment globalEnv, MethodType currMethod)
    {
        m_globalEnvironment = globalEnv;
        m_scope             = currMethod;
        m_localVariables    = null;
    }

    boolean containsLocalVariable(VarType v)
    {
        return m_localVariables.containsEntry(v.variableName());
    }

    void addLocalVariable(VarType v)
    {
        m_localVariables.addEntry(v);
    }

    VarType getVariable(String varName)
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
}
