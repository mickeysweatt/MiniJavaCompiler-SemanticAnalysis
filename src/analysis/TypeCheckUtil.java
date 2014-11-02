package analysis;

import environment.Environment;
import environment.Type;
import syntaxtree.*;

import java.util.LinkedList;

import static analysis.TypeError.close;

/**
 * Created by michael on 10/26/14.
 */
public class TypeCheckUtil {
    // This class acts as a Utility class for type checking a MiniJava Program.

    static void TypeCheckBinaryArithLogExpression(TypeCheckVisitor  v,
                                                  Environment       env,
                                                  PrimaryExpression lhs,
                                                  PrimaryExpression rhs,
                                                  Type expectedArgType)
    // This method performs type checking for an arbitrary binary expression. This method is successful if the type
    // of specified 'lhs' and 'rhs' expression match the specified 'expectedArgType' and are equal. Otherwise this
    // method calls close().
    //
    {
        Type lhs_type = lhs.accept(v, env);
        Type rhs_type = rhs.accept(v, env);
        if (expectedArgType !=  lhs_type || expectedArgType != rhs_type)
        {
            close("Expression expects 2 ints");
        }
    }

    static LinkedList<Type> getArgumentTypes(Node curr, Environment env)
    // This method take a parameter list and converts it to an equivalent LinkedList of 'VarTypes' maintaining the order
    // of the declarations. The behavior is undefined unless curr references a argument list.
    {
        Expression       curr_node;  // reference to head of list (if any)
        ExpressionList   arg_list;   // the arg_list starting at this point (if any)
        NodeListOptional rest;       // a reference to the rest of the list (if any)
        LinkedList<Type> types;      // the types for the remaining parameters
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
