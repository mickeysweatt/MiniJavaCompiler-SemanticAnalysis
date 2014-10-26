package environment;

import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static analysis.TypeError.close;

/**
 * Created by michael on 10/25/14.
 */
public class EnvironmentBuilderUtil {
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
                close("Redeclaring parameter " + parameterName + " in " + m.getName());
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

}


