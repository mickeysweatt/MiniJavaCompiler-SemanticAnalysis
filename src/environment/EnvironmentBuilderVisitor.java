package environment;

import analysis.TypeError;
import environment.*;
import environment.Type;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.Enumeration;

import static analysis.TypeError.*;


/**
 * Created by admin on 10/23/14.
 */

class EnvironmentBuilderUtil {
    // expects the class type of the class where the method resides
    public static boolean addInstanceVariablesToClass(NodeListOptional varList, ClassType curr_class, GlobalEnvironment env)
    {
        for ( Enumeration<Node> e = varList.elements(); e.hasMoreElements(); ) {
            VarType instanceVar = EnvironmentUtil.vardecl((VarDeclaration) e.nextElement(), env);
            instanceVar.setScope(curr_class);
            if (curr_class.containsInstanceVar(instanceVar))
            {
                close("Redefining instance variable " + instanceVar.variableName() +
                        " in class " + curr_class.getClassName());
            }
            curr_class.addInstanceVar(instanceVar);
        }

        return true;
    }

    public static boolean addMethodToClass(MethodDeclaration m, ClassType curr_class, GlobalEnvironment env)
    {
        String method_name = EnvironmentUtil.methodname(m);
        Type returnType = EnvironmentUtil.SyntaxTreeTypeToEnvironmentType(m.type.nodeChoice.choice, env);
        if (curr_class.containsMethod(method_name)) {
            close("Redefining method " + method_name + " in class " + curr_class.getClassName());
            return false;
        }
        else {
            MethodType method = new MethodType(method_name, returnType, curr_class, null);
            getParameterListForMethod(m.nodeOptional.node, method, env);
            curr_class.addMethod(method);
        }
        return true;
    }

   public static void getParameterListForMethod(Node parameter, MethodType m, GlobalEnvironment env)
   {
       if (null == parameter)
       {
           return;
       }
       else if (parameter instanceof  FormalParameterRest)
       {
           getParameterListForMethod(((FormalParameterRest)parameter).formalParameter, m, env);
       }

       else if (parameter instanceof FormalParameter)
       {
           FormalParameter fp = (FormalParameter) parameter;
           environment.Type parameterType = EnvironmentUtil.SyntaxTreeTypeToEnvironmentType(fp.type.nodeChoice.choice, env);
           String parameterName = fp.identifier.nodeToken.toString();
           VarType parameter_type = new VarType(parameterType, parameterName, m);
           if (!m.containsParameter(parameter_type)) {
                m.addParameter(parameter_type);
            } else {
                close("Redeclaring parameter " + parameterName + " in " + m.getName());
            }
       }
       else if (parameter instanceof FormalParameterList)
       {
           FormalParameterList pl = (FormalParameterList) parameter;
           getParameterListForMethod(pl.formalParameter, m, env);
           for (Node n : pl.nodeListOptional.nodes)
           {
               getParameterListForMethod(n, m, env);
           }
           return;
       }
   }
}
public class EnvironmentBuilderVisitor extends GJDepthFirst<Integer, GlobalEnvironment> {
    private ClassType m_currentClass;

    public Integer visit(Goal g, GlobalEnvironment env)
    {
        // goes through the first pass just to get the class names
        ClassNameVisitor v = new ClassNameVisitor();
        v.visit(g, env);
        super.visit(g, env);
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

    public Integer visit (ClassDeclaration d, GlobalEnvironment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(d);
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
