package analysis;

import environment.*;
import environment.Type;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TimerTask;

/**
 * Created by admin on 10/23/14.
 */
public class TypeCheckVisitor extends GJDepthFirst<environment.Type, Environment> {

    public Type visit(ClassDeclaration d, Environment env)
    {
        Environment curr_env =  EnvironmentUtil.buildLocalEnvironment(d, env);
        Type rval;
        rval = d.nodeListOptional.accept(this, curr_env);
        rval = d.nodeListOptional1.accept(this, curr_env);

        /* null is used for statements which have no value */
        return rval;
    }

    public Type visit(Expression e, Environment env)
    {
        return e.nodeChoice.accept(this, env);
    }

    public Type visit(MinusExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this, env, e.primaryExpression, e.primaryExpression1);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(PlusExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this, env, e.primaryExpression, e.primaryExpression1);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(TimesExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this, env, e.primaryExpression, e.primaryExpression1);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(AndExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this, env, e.primaryExpression, e.primaryExpression1);
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(CompareExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this, env, e.primaryExpression, e.primaryExpression1);
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(Identifier id, Environment env)
    {
        Type rval = null;
        String id_name = EnvironmentUtil.identifierToString(id);
        // we only care to look-ups on variables, all other ids are handle elsewhere.
        if (env instanceof ScopedEnvironment)
        {
            ScopedEnvironment curr_env = (ScopedEnvironment) env;
            VarType var = curr_env.getVariable(id_name);
            if (null != var)
            {
                // return the type of the variable
                rval = var.variableType();
            }
        }
        return rval;
    }

    public Type visit(PrintStatement p, Environment env)
    {
        if (PrimitiveType.INT_TYPE !=  p.expression.accept(this, env))
        {
            analysis.TypeError.close("println expects parameter type 'int'");
        }
        return null;
    }

    public Type visit(MessageSend ms, Environment env)
    {
        // lhs must be a class
        Type lhs_type = ms.primaryExpression.accept(this, env);
        Type rval = null;
        if (lhs_type instanceof  ClassType) {
            // rhs must be a member of that class
            String method_name = EnvironmentUtil.identifierToString(ms.identifier);
            MethodType rhs =  ((ClassType) lhs_type).getMethod(method_name);
            if (null != rhs)
            {
                // check all the parameters.
                LinkedList<Type>    passed_parameters = TypeCheckUtil.getArgumentTypes(ms.nodeOptional.node, env);
                LinkedList<VarType> method_parameters = rhs.getParameterList();
                if (passed_parameters.size() != method_parameters.size())
                {
                    analysis.TypeError.close("Parameter count is wrong for " + method_name);
                }
                Iterator passed_iter = passed_parameters.iterator();
                Iterator method_iter = method_parameters.iterator();

                while (passed_iter.hasNext() && method_iter.hasNext()) {
                    Type passed_type = (Type) passed_iter.next();
                    Type method_type = ((VarType) method_iter.next()).variableType();
                    if (!passed_type.equals(method_type))
                    {
                       TypeError.close("Type mismatch for " + method_name);
                    }
                }


                // type of this expression is the return type of the method called
                rval = rhs.getReturnType();
            }
            else
            {
                TypeError.close("Undefined method " + method_name);
            }
        }
        else {
           TypeError.close("Message passing to non-class type");
        }


        return rval;
    }

    public Type visit(ThisExpression t, Environment env)
    {
        // Get scoping method
        MethodType curr_method = (MethodType)((ScopedEnvironment)env).getScope();
        // return enclosing class
        return curr_method.getScope();
    }

    public Type visit(AssignmentStatement a, Environment env)
    {

        Type lhs_type = a.identifier.accept(this, env);
        Type rhs_type = a.expression.accept(this, env);

        if (!lhs_type.subtype(rhs_type))
        {
            TypeError.close("Type mismatch" + lhs_type.typeName() + ", " + rhs_type.typeName());
        }
        return lhs_type;
    }

    public Type visit (AllocationExpression alloc, Environment env) {
        String class_name = EnvironmentUtil.identifierToString(alloc.identifier);
        // make sure the RHS is valid class name
        ClassType rhs = env.getGlobalEnvironment().getClass(class_name);
        if (null == rhs) {
            analysis.TypeError.close("Undeclared identifer" + class_name);
        }
        return rhs;
    }


    public Type visit(MethodDeclaration m, Environment env)
    {
        Environment curr_env = EnvironmentUtil.buildLocalEnvironment(m, env);
        m.nodeToken.accept(this, curr_env);
        m.type.accept(this, curr_env);
        m.nodeOptional.accept(this, curr_env);
        m.nodeListOptional.accept(this, curr_env);
        m.nodeListOptional1.accept(this, curr_env);
        m.expression.accept(this, curr_env);
        /* null is used for statements which have no value */
        return null;
    }

    public Type visit(IntegerLiteral l, Environment env)
    {
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(TrueLiteral l, Environment env)
    {
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(FalseLiteral l, Environment env)
    {
        return PrimitiveType.BOOL_TYPE;
    }

}


