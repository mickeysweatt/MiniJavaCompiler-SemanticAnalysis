package environment; /**
 * Author: Mickey Sweatt
 */

import analysis.TypeError;
import syntaxtree.*;

public class EnvironmentUtil {
    public static String classname(Node n) {
        if (n instanceof MainClass) {
            return identifierToString(((MainClass) n).f1);
        } else if (n instanceof ClassDeclaration) {
            return identifierToString(((ClassDeclaration) n).f1);
        } else if (n instanceof ClassExtendsDeclaration) {
            return identifierToString((((ClassExtendsDeclaration) n)).f1);
        } else {
            System.out.println("classname method called with incorrect parameter!");
            System.exit(-1);
            return null;
        }
    }

    public static String methodname(MethodDeclaration m) {
        return identifierToString(m.f2);
    }

    public static VarType vardecl(VarDeclaration v, GlobalEnvironment env) {
        Type c = SyntaxTreeTypeToEnvironmentType(v.f0.f0.choice, env);
        String varName = identifierToString(v.f1);
        return new VarType(c, varName, null);
    }

    public static Type SyntaxTreeTypeToEnvironmentType(Node syntaxTreeType, GlobalEnvironment env) {
        if (syntaxTreeType instanceof IntegerType) {
            return PrimitiveType.INT_TYPE;
        } else if (syntaxTreeType instanceof BooleanType) {
            return PrimitiveType.BOOL_TYPE;
        } else if (syntaxTreeType instanceof ArrayType) {
            return new IntArrayType();
        } else if (syntaxTreeType instanceof Identifier) {
            String class_name = identifierToString((Identifier) syntaxTreeType);
            ClassType t = env.getClass(class_name);
            if (null == t) {
                TypeError.close("Undefined class " + class_name + " used in paramater ");
            }
            return t;
        }
        System.err.println("Type not known");
        return null;
    }

    public static String identifierToString(Identifier id) {
        return id.f0.toString();
    }

}
