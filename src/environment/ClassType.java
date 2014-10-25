package environment;

import java.util.*;

public class ClassType implements Type {
    private String m_name;
    private Set<ClassType> m_superClasses;
    private Map<String, MethodType> m_methods;
    private Set<VarType> m_instanceVars;

    public String typeName() {
        return m_name;
    }

    public ClassType(String name) {
        m_name = name;
        m_superClasses = null;
        m_methods = null;
        m_instanceVars = null;
    }

    public void addSuperClass(ClassType t) {
        if (null == m_superClasses) {
            m_superClasses = new TreeSet<ClassType>();
        }
        m_superClasses.add(t);
    }

    public void addMethod(MethodType m) {
        if (null == m_methods) {
            m_methods = new HashMap<String, MethodType>();
        }
        m_methods.put(m.getName(), m);
    }

    public MethodType getMethod(String method_name) {
        if (null != m_methods) {
            m_methods.get(method_name);
        }
        return null;
    }

    public boolean containsMethod(String method_name) {
        return (null != m_methods) && (m_methods.containsKey(method_name));
    }


    public String getClassName() {
        return m_name;
    }

    public void addInstanceVar(VarType v) {
        if (null == m_instanceVars) {
            m_instanceVars = new HashSet<VarType>();
        }
        m_instanceVars.add(v);
    }

    public boolean containsInstanceVar(VarType v) {
        boolean rval = false;
        if (null != m_instanceVars) {
            for (VarType vt : m_instanceVars) {
                if (v.variableName() == v.variableName()) {
                    rval = true;
                    break;
                }
            }
        }
        return rval;
    }
}
