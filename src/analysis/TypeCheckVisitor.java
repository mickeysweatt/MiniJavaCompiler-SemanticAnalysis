package analysis;

import environment.*;
import syntaxtree.*;
import visitor.GJDepthFirst;

/**
 * Created by admin on 10/23/14.
 */
public class TypeCheckVisitor extends GJDepthFirst<environment.Type, Environment> {



    public environment.Type visit(IntegerLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.INT_TYPE;
    }

    public environment.Type visit(TrueLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.BOOL_TYPE;
    }

    public environment.Type visit(FalseLiteral l, Environment env)
    {
        super.visit(l, env);
        return PrimitiveType.BOOL_TYPE;
    }

}


