package environment;

import analysis.TypeError;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.*;

import static analysis.TypeError.close;

/**
 * Created by michael on 10/25/14.
 */
public class EnvironmentBuilderUtil {

    public static void flattenSubtyping(GlobalEnvironment env)
    {
        Map<String, ClassType> classes = env.getClasses();

        for (Map.Entry<String, ClassType> pair : classes.entrySet())
        {
            ClassType cl = pair.getValue();
            getSuperClasses(cl, null);
        }

    }


    private static void getSuperClasses(ClassType c, Set<ClassType> visited)
    {
        if (null == visited)
        {
            visited = new HashSet<ClassType>();
        }
        else if (visited.contains(c))
        {
            TypeError.close("Cycle detected in class hierarchy.");
        }
        visited.add(c);
        // get first value out of the super classes (should only be one if any at this point)
        Set<ClassType> supList = c.getSuperClasses();
        if (null == supList)
        {
            return;
        }
        else
        {
            // get direct super
            ClassType sup = supList.iterator().next();
            // fill out super classes of direct super
            getSuperClasses(sup, visited);
            supList = sup.getSuperClasses();
            if (supList != null) {
                c.getSuperClasses().addAll(sup.getSuperClasses());
            }
        }
    }

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
            MethodType method;
            method = new MethodType(method_name, returnType, curr_class, null);
            getVariableList(m.nodeOptional.node, method, env);
            curr_class.addMethod(method);
        }
        return true;
    }

    public static void getVariableList(Node parameter, MethodType m, GlobalEnvironment env)
    {
        if (null == parameter)
        {
            return;
        }
        else if (parameter instanceof FormalParameterRest)
        {
            getVariableList(((FormalParameterRest) parameter).formalParameter, m, env);
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
                close("Redeclaring parameter " + parameterName + " in " + m.typeName());
            }
        }
        else if (parameter instanceof FormalParameterList)
        {
            FormalParameterList pl = (FormalParameterList) parameter;
            getVariableList(pl.formalParameter, m, env);
            for (Node n : pl.nodeListOptional.nodes)
            {
                getVariableList(n, m, env);
            }
            return;
        }
    }

    public static void getVariableList(Vector<Node> variableDecls, Environment localVars, GlobalEnvironment env)
    {
        for (Node node : variableDecls)
        {
            VarDeclaration decl = (VarDeclaration) node;
            Type varType = EnvironmentUtil.SyntaxTreeTypeToEnvironmentType(decl.type.nodeChoice.choice, env);
            String varName = decl.identifier.nodeToken.toString();
            VarType vt = new VarType(varType, varName, null);
            if (!localVars.containsEntry(vt.variableName()))
            {
                localVars.addEntry(vt);
            }
            else
            {
                close("Redeclaring parameter");
            }
        }
    }

    public static ScopedEnvironment buildLocalEnvironment(ClassDeclaration classDeclaration, Environment env)
    {
        GlobalEnvironment g_env = (GlobalEnvironment) env;
        String class_name    = classDeclaration.identifier.nodeToken.toString();
        ClassType curr_class = g_env.getClass(class_name);
        ScopedEnvironment curr_env = new ScopedEnvironment(g_env, curr_class);
        return  curr_env;
    }

    public static ScopedEnvironment  buildLocalEnvironment(MethodDeclaration methodDeclaration, Environment env)
    {
        GlobalEnvironment g_env = ((ScopedEnvironment)env).getGlobalEnvironment();
        String method_name = methodDeclaration.identifier.nodeToken.toString();
        ClassType scoping_class = (ClassType)((ScopedEnvironment)env).getScope();
        MethodType curr_method = scoping_class.getMethod(method_name);
        ScopedEnvironment curr_env = new ScopedEnvironment(g_env, curr_method);
        getVariableList(methodDeclaration.nodeListOptional.nodes, curr_env.getLocalVariables(), g_env);

        // add parameters to local variables
        LinkedList<VarType> parameterList = curr_method.getParameterList();
        if (null != parameterList) {
            for( VarType v : parameterList)
            {
                curr_env.addLocalVariable(v);
            }
        }

        return curr_env;
    }
}


