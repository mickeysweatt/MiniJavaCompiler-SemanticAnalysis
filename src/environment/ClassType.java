package environment;

import java.util.*;

public class ClassType implements Type {
    private String m_name;
    private Set<ClassType> m_superClasses;
    private Environment<MethodType> m_methods;
    private Environment<VarType> m_instanceVars;

    public String typeName() {
        return m_name;
    }

    public String toString()
    {
        return typeName();
    }

    public ClassType(String name) {
        m_name = name;
        m_superClasses = null;
        m_methods = new Environment<MethodType>();
        m_instanceVars = new Environment<VarType>();
    }

    public void addSuperClass(ClassType t) {
        if (null == m_superClasses) {
            m_superClasses = new TreeSet<ClassType>();
        }
        m_superClasses.add(t);
    }

    public void addMethod(MethodType m) {
        m_methods.addEntry(m);
    }

    public MethodType getMethod(String method_name)  {
            return m_methods.getEntry(method_name);
    }

    public boolean containsMethod(String method_name) {
        return m_methods.containsEntry(method_name);
    }

    public String getClassName() {
        return m_name;
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
}
