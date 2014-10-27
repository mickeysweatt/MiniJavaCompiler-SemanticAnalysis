package analysis;

import environment.Environment;
import environment.PrimitiveType;
import environment.Type;
import syntaxtree.*;

import java.util.LinkedList;
import java.util.List;

import static analysis.TypeError.*;

/**
 * Created by michael on 10/26/14.
 */
public class TypeCheckUtil {

    static void TypeCheckBinaryArithLogExpression(TypeCheckVisitor  v,
                                                  Environment       env,
                                                  PrimaryExpression lhs,
                                                  PrimaryExpression rhs,
                                                  Type expectedArgType)
    {
        Type lhs_type = lhs.accept(v, env);
        Type rhs_type = rhs.accept(v, env);
        if (expectedArgType !=  lhs_type || expectedArgType != rhs_type)
        {
            close("Expression expects 2 ints");
        }
        return;
    }

    static LinkedList<Type> getArgumentTypes(Node curr, Environment env)
    {
        Expression       curr_node = null;   // reference to head of list (if any)
        ExpressionList   arg_list  = null;  // the arg_list starting at this point (if any)
        NodeListOptional rest      = null;  // a reference to the rest of the list (if any)
        LinkedList<Type> types     = null;  // the types for the remaining parameters
        // no more parameters
        if (curr == null)
        {
            return null;
        }

        else
        {
            arg_list = (ExpressionList) curr;
            curr_node = arg_list.expression;
            rest = arg_list.nodeListOptional;

            TypeCheckVisitor v = new TypeCheckVisitor();
            types = new LinkedList<Type>();

            // type check the head of the list
            types.add(v.visit(curr_node, env));

            // type check the rest
            for (Node n : rest.nodes)
            {
                Expression next = ((ExpressionRest)n).expression;
                types.add(v.visit(next, env));
            }

        }
        return types;
    }
}
