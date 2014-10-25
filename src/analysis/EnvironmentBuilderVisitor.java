package analysis;

import environment.*;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.Enumeration;


/**
 * Created by admin on 10/23/14.
 */

class EnvironmentBuilderUtil {
    // expects the class type of the class where the method resides
    public static boolean addInstanceVariablesToClass(NodeListOptional varList, ClassType t, Environment env)
    {
        for ( Enumeration<Node> e = varList.elements(); e.hasMoreElements(); ) {
            VarType instanceVar = EnvironmentUtil.vardecl((VarDeclaration) e.nextElement(), env);
            if (t.containsInstanceVar(instanceVar))
            {
                TypeError.close("Redefining instance variable " + instanceVar.variableName() +
                                " in class " + t.getClassName());
            }
            t.addInstanceVar(instanceVar);
        }

        return true;
    }

    public static boolean addMethodToClass(MethodDeclaration m, ClassType t, Environment env)
    {
        String method_name = EnvironmentUtil.methodname(m);
        environment.Type returnType = EnvironmentUtil.SyntaxTreeTypeToEnvironmentType(m.type.nodeChoice.choice, env);
        if (t.containsMethod(method_name)) {
            TypeError.close("Redefining method " + method_name + " in class " + t.getClassName());
            return false;
        }
        else {
            MethodType method = new MethodType(method_name, returnType, null);
            getParameterListForMethod(m.identifier.nodeToken, method, env);
            t.addMethod(method);
        }
        return true;
    }

   public static void getParameterListForMethod(Node parameter, MethodType m, Environment env)
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
           VarType parameter_type = new VarType(parameterType, parameterName);
            if (m.containsParameter(parameter_type))
            {
                TypeError.close("Redeclaring parameter " + parameterName + " in " + m.getName());
            }
           else {
                m.addParameter(parameter_type);
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
public class EnvironmentBuilderVisitor extends GJDepthFirst<Integer, Environment> {
    private ClassType m_currentClass;

    public Integer visit(Goal g, Environment env)
    {
        // goes through the first pass just to get the class names
        ClassNameVisitor v = new ClassNameVisitor();
        v.visit(g, env);
        super.visit(g, env);
        return null;
    }

    // assumes all class names are in env
    public Integer visit (ClassDeclaration d, Environment env)
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

    public Integer visit (MethodDeclaration m, Environment env)
    {
        EnvironmentBuilderUtil.addMethodToClass(m, m_currentClass, env);
        super.visit(m, env);
        return null;
    }
}

// Just completes first pass to get the class names
class ClassNameVisitor extends GJDepthFirst<Integer, Environment> {
    public Integer visit(MainClass m, Environment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(m);
        if (env.containsClass(class_name))
        {
            TypeError.close("Redefining class " + class_name);
            rval = -1;
        }
        else {
            env.addClass(new ClassType(class_name));
        }
        super.visit(m, env);
        return rval;
    }

    public Integer visit (ClassDeclaration d, Environment env)
    {
        Integer rval = 0;
        String class_name = EnvironmentUtil.classname(d);
        if (env.containsClass(class_name))
        {
            TypeError.close("Redefining class " + class_name);
            rval = -1;

        }
        else {
            env.addClass(new ClassType(class_name));
        }
        super.visit(d, env);
        return rval;
    }

}
