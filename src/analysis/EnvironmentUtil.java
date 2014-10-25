package analysis; /**
 * Created by admin on 10/23/14.
 */
import environment.VarType;
import environment.Environment;
import environment.PrimitiveType;
import environment.ClassType;
import environment.Type;
import syntaxtree.*;

public class EnvironmentUtil {
    public static String classname(Node n)
    {
        if (n instanceof MainClass) {
            return ((MainClass) n).f1.f0.toString();
        }
        else if (n instanceof ClassDeclaration) {
            return ((ClassDeclaration) n).f1.f0.toString();
        }
        else if (n instanceof ClassExtendsDeclaration) {
            return ((ClassExtendsDeclaration) n).f1.f0.toString();
        }
        else {
            System.out.println("classname method called with incorrect parameter!");
            System.exit(-1);
            return null;
        }
    }

    public static String methodname(MethodDeclaration m)
    {
        return m.f2.f0.toString();
    }

    public static VarType vardecl(VarDeclaration v, Environment env)
    {
        Type c    = SyntaxTreeTypeToEnvironmentType(v.f0.f0.choice, env);
        String varName = v.f1.f0.toString();
        return new VarType(c, varName);
    }

    public static environment.Type SyntaxTreeTypeToEnvironmentType(Node syntaxTreeType, Environment env)
    {
        if (syntaxTreeType instanceof IntegerType)
        {
            return PrimitiveType.INT_TYPE;
        }
        else if (syntaxTreeType instanceof  BooleanType)
        {
            return PrimitiveType.BOOL_TYPE;
        }
        else if (syntaxTreeType instanceof Identifier)
        {
            String class_name = ((Identifier)syntaxTreeType).f0.toString();
            ClassType t = env.getClass(class_name);
            if (null == t)
            {
                TypeError.close("Undefined class " + class_name  + " used in paramater ");
            }
            return t;
        }
        System.err.println("Type not known");
        return null;
    }

}
