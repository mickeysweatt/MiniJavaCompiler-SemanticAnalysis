package environment; /**
 * Created by admin on 10/23/14.
 */
import analysis.TypeError;
import environment.VarType;
import environment.Environment;
import environment.PrimitiveType;
import environment.ClassType;
import environment.Type;
import syntaxtree.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class EnvironmentUtil {
    public static String classname(Node n)
    {
        if (n instanceof MainClass) {
            return ((MainClass) n).identifier.nodeToken.toString();
        }
        else if (n instanceof ClassDeclaration) {
            return ((ClassDeclaration) n).identifier.nodeToken.toString();
        }
        else if (n instanceof ClassExtendsDeclaration) {
            return ((ClassExtendsDeclaration) n).identifier.nodeToken.toString();
        }
        else {
            System.out.println("classname method called with incorrect parameter!");
            System.exit(-1);
            return null;
        }
    }

    public static String methodname(MethodDeclaration m)
    {
        return m.identifier.nodeToken.toString();
    }

    public static VarType vardecl(VarDeclaration v, GlobalEnvironment env)
    {
        Type c    = SyntaxTreeTypeToEnvironmentType(v.type.nodeChoice.choice, env);
        String varName = v.identifier.nodeToken.toString();
        return new VarType(c, varName, null);
    }

    public static Type SyntaxTreeTypeToEnvironmentType(Node syntaxTreeType, GlobalEnvironment env)
    {
        if (syntaxTreeType instanceof IntegerType)
        {
            return PrimitiveType.INT_TYPE;
        }

        else if (syntaxTreeType instanceof  BooleanType)
        {
            return PrimitiveType.BOOL_TYPE;
        }

        else if (syntaxTreeType instanceof ArrayType)
        {
            return new IntArrayType();
        }

        else if (syntaxTreeType instanceof Identifier)
        {
            String class_name = ((Identifier)syntaxTreeType).nodeToken.toString();
            ClassType t = env.getClass(class_name);
            if (null == t)
            {
                TypeError.close("Undefined class " + class_name + " used in paramater ");
            }
            return t;
        }
        System.err.println("Type not known");
        return null;
    }

    public static String identifierToString(Identifier id)
    {
        return id.nodeToken.toString();
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
        EnvironmentBuilderUtil.getVariableList(methodDeclaration.nodeListOptional.nodes, curr_env.getLocalVariables(), g_env);

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
