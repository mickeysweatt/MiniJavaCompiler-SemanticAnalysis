package environment;

import java.util.Map;

public class GlobalEnvironment extends Environment<ClassType> {
    public Map<String, ClassType> getClasses()
    {
        return getEntries();
    }

    public ClassType getClass(String class_name)
    {
        return getEntry(class_name);
    }

    public void addClass(ClassType c) { addEntry(c);}

    public GlobalEnvironment getGlobalEnvironment()
    {
        return this;
    }
}
