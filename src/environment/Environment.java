package environment; /**
 * Created by Mickey Sweatt
 * 22 Oct 2014
 */

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, ClassType> m_classes;

    public Environment()
    {
        m_classes = null;
    }

    public Environment(Environment other)
    {
        if (null == other.getClasses())
        {
            m_classes = null;
            return;
        }

        m_classes = new HashMap<String, ClassType>();
        for (String class_name : other.getClasses().keySet())
        {
            m_classes.put(class_name, other.getClasses().get(class_name));
        }
    }

    public void addClass(ClassType c)
    {
        if (null == m_classes) {
            m_classes = new HashMap<String, ClassType>();
        }
        m_classes.put(c.getClassName(), c);
    }

    public boolean containsClass(String class_name)
    {
       return null != getClass(class_name);
    }

    public ClassType getClass(String class_name)
    {
        if (null != m_classes) {
            return m_classes.get(class_name);
        }
        return null;
    }

    public Map<String, ClassType> getClasses()
    {
        return m_classes;
    }

    
}


