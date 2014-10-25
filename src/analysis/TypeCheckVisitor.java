package analysis;

import environment.*;
import environment.Type;
import syntaxtree.*;
import visitor.GJDepthFirst;

/**
 * Created by admin on 10/23/14.
 */
public class TypeCheckVisitor extends GJDepthFirst<environment.Type, Environment> {

    public Type visit(ClassDeclaration d, Environment env)
    {
        Environment curr_env =  EnvironmentUtil.buildLocalEnvironment(d, env);

        super.visit(d, curr_env);
        /* null is used for statements which have no value */
        return null;
    }

    public Type visit(MethodDeclaration m, Environment env)
    {
        Environment curr_env = EnvironmentUtil.buildLocalEnvironment(m, env);
        super.visit(m, curr_env);
        /* null is used for statements which have no value */
        return null;
    }

    public Type visit(IntegerLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(TrueLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(FalseLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.BOOL_TYPE;
    }

}


