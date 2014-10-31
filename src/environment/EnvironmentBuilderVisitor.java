package environment;

import analysis.TypeError;
import syntaxtree.*;
import visitor.GJDepthFirst;

import static analysis.TypeError.close;


/**
 * Created by admin on 10/23/14.
 */
public class EnvironmentBuilderVisitor extends GJDepthFirst<Object, GlobalEnvironment> {
    private ClassType m_currentClass;

    public Integer visit(Goal g, GlobalEnvironment env)
    {
        // goes through the first pass just to get the class names
        ClassNameVisitor v = new ClassNameVisitor();
        v.visit(g, env);
        super.visit(g, env);
        return null;
    }

    public Integer visit(MainClass m, GlobalEnvironment env)
    {
        // add the arg list as a parameter, which cannot be accessed?
        String mainClassName = EnvironmentUtil.identifierToString(m.identifier);
        String argsName = EnvironmentUtil.identifierToString(m.identifier1);
        ClassType main = env.getClass(mainClassName);
        MethodType mainMethod = new MethodType("main", null, main, null);
        mainMethod.addParameter(new VarType(null, argsName, main));
        main.addMethod(mainMethod);

        m_currentClass = main;
        super.visit(m, env);
        return null;
    }

    // assumes all class names are in env
    public Integer visit (ClassDeclaration d, GlobalEnvironment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(d);
        ClassType curr_class = env.getClass(class_name);
        EnvironmentBuilderUtil.addInstanceVariablesToClass(d.nodeListOptional, curr_class, env);
        env.addClass(curr_class);
        m_currentClass = curr_class;
        super.visit(d, env);
        return rval;
    }

    public Integer visit (ClassExtendsDeclaration d, GlobalEnvironment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(d);
        String super_name = EnvironmentUtil.identifierToString(d.identifier1);
        ClassType curr_class  = env.getClass(class_name);
        ClassType super_class = env.getClass(super_name);
        if (null == super_class)
        {
            TypeError.close("Undeclared identifer " + super_name);
        }
        EnvironmentBuilderUtil.addInstanceVariablesToClass(d.nodeListOptional, curr_class, env);
        curr_class.addSuperClass(super_class);
        env.addClass(curr_class);
        m_currentClass = curr_class;
        super.visit(d, env);
        return rval;
    }

    public Integer visit (MethodDeclaration m, GlobalEnvironment env)
    {
        EnvironmentBuilderUtil.addMethodToClass(m, m_currentClass, env);
        super.visit(m, env);
        return null;
    }
}



// Just completes first pass to get the class names
class ClassNameVisitor extends GJDepthFirst<Integer, GlobalEnvironment> {
    public Integer visit(MainClass m, GlobalEnvironment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(m);
        if (!env.containsEntry(class_name))
        {
            env.addClass(new ClassType(class_name));
        }
        else
        {
            close("Redefining class " + class_name);
            rval = -1;
        }
        super.visit(m, env);
        return rval;
    }

    public Integer visit (TypeDeclaration d, GlobalEnvironment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(d.nodeChoice.choice);
        if (env.containsEntry(class_name))
        {
            close("Redefining class " + class_name);
            rval = -1;

        }
        else {
            env.addClass(new ClassType(class_name));
        }
        super.visit(d, env);
        return rval;
    }
}
