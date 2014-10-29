package environment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ClassType extends ScopedType {
    private Set<ClassType> m_superClasses;
    private Environment<MethodType> m_methods;
    private Environment<VarType> m_instanceVars;

    public String toString()
    {
        return typeName();
    }

    public ClassType(String name) {
        super(name, null);
        m_superClasses = null;
        m_methods = new Environment<MethodType>();
        m_instanceVars = new Environment<VarType>();
    }

    public void addSuperClass(ClassType t) {
        if (null == m_superClasses) {
            m_superClasses = new HashSet<ClassType>();
        }
        m_superClasses.add(t);
    }

    public Set<ClassType> getSuperClasses()
    {
        return m_superClasses;
    }

    public boolean subtype(Type rhs)
    {
        if (rhs.equals(this))
        {
            return true;
        }
        return m_superClasses.contains(rhs);
    }

    public void addMethod(MethodType m) {
        m_methods.addEntry(m);
    }

    public MethodType getMethod(String method_name)  {
            return m_methods.getEntry(method_name);
    }

    public Collection<MethodType> getMethods()
    {
        return m_methods.getEntries().values();
    }

    public boolean containsMethod(String method_name) {
        return m_methods.containsEntry(method_name);
    }

    public String getClassName() {
        return toString();
    }

    public void addInstanceVar(VarType v) {
        m_instanceVars.addEntry(v);
    }

    public boolean containsInstanceVar(VarType v) {
        return m_instanceVars.containsEntry(v.variableName());
    }

    public VarType getInstanceVar(String varName) {
        return m_instanceVars.getEntry(varName);
    }

    public Collection<VarType> getInstanceVariables() {
        return (null == m_instanceVars || m_instanceVars.getEntries() == null) ? null :
                                                                                 m_instanceVars.getEntries().values();
    }
}
