package analysis;

import environment.*;
import environment.Type;
import syntaxtree.*;
import visitor.GJDepthFirst;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Author: Mickey Sweatt
 */
public class TypeCheckVisitor extends GJDepthFirst<environment.Type, Environment> {

    // Declarations Checks
    public Type visit(MainClass m, Environment env)
    {
        Environment curr_env =  EnvironmentBuilderUtil.buildLocalEnvironment(m, env);
        Type rval;
        //m.nodeListOptional.accept(this, curr_env);
        rval = m.f15.accept(this, curr_env);

        /* null is used for statements which have no value */
        return rval;
    }


    public Type visit(ClassDeclaration d, Environment env)
    {
        Environment curr_env =  EnvironmentBuilderUtil.buildLocalEnvironment(d, env);
        Type rval;
        rval = d.f4.accept(this, curr_env);

        return rval;
    }

    public Type visit(ClassExtendsDeclaration d, Environment env)
    {
        Environment curr_env =  EnvironmentBuilderUtil.buildLocalEnvironment(d, env);

        d.f5.accept(this, curr_env);
        d.f6.accept(this, curr_env);

        /* null is used for statements which have no value */
        return null;
    }

    public Type visit(MethodDeclaration m, Environment env)
    {
        ScopedEnvironment curr_env = EnvironmentBuilderUtil.buildLocalEnvironment(m, env);
        MethodType curr_method = (MethodType) curr_env.getScope();

        m.f1.accept(this, curr_env.getGlobalEnvironment());
        //m.nodeOptional.accept(this, curr_env);
        m.f8.accept(this, curr_env);
        Type returnType = m.f10.accept(this, curr_env);

        if (null == curr_method || !returnType.subtype(curr_method.getReturnType()))
        {
            TypeError.close(returnType + " is not a subtype of " + curr_method.getReturnType());
        }

        /* null is used for statements which have no value */
        return null;
    }

    public Type visit(Identifier id, Environment env)
    {
        Type rval = null;
        String id_name = EnvironmentUtil.identifierToString(id);
        // we only care to look-ups on variables,not for declarations, all other ids are handle elsewhere.
        if (env instanceof ScopedEnvironment && ((ScopedEnvironment) env).getScope() instanceof MethodType)
        {
            ScopedEnvironment curr_env = (ScopedEnvironment) env;
            VarType var = curr_env.getVariable(id_name);
            if (null != var)
            {
                // return the type of the variable
                rval = var.variableType();
            }
            if (null == rval)
            {
                TypeError.close("Undeclared identifier " + id_name + " in scope " + curr_env.getScope());
            }
        }

        return rval;
    }

    // Complex type checks
    public Type visit(MessageSend ms, Environment env)
    {
        // lhs must be a class
        Type lhs_type = ms.f0.accept(this, env);
        Type rval = null;
        if (null != lhs_type && lhs_type instanceof  ClassType) {
            // rhs must be a member of that class
            String method_name = EnvironmentUtil.identifierToString(ms.f2);
            MethodType rhs =  ((ClassType) lhs_type).getMethod(method_name);
            if (null != rhs)
            {
                // check all the parameters.
                LinkedList<Type> passed_parameters = TypeCheckUtil.getArgumentTypes(ms.f4.node, env);
                LinkedList<VarType> method_parameters = rhs.getParameterList();
                if (null == passed_parameters)
                {
                    if (0 != method_parameters.size()) {
                        // one expects no parameters and the other does
                        analysis.TypeError.close("Parameter count is wrong for " + method_name);
                    }
                    // else fall through
                }
                else if (passed_parameters.size() != method_parameters.size() )
                {
                    analysis.TypeError.close("Parameter count is wrong for " + method_name);
                }
                else {
                    Iterator passed_iter = passed_parameters.iterator();
                    Iterator method_iter = method_parameters.iterator();

                    while (passed_iter.hasNext() && method_iter.hasNext()) {
                        Type passed_type = (Type) passed_iter.next();
                        Type method_type = ((VarType) method_iter.next()).variableType();
                        if (!passed_type.subtype(method_type)) {
                            TypeError.close("Type mismatch for " + method_name);
                        }
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

    // Expressions
    public Type visit(Expression e, Environment env)
    {
        return e.f0.accept(this, env);
    }

    public Type visit(MinusExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this,
                env,
                e.f0,
                e.f2,
                PrimitiveType.INT_TYPE);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(PlusExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this,
                env,
                e.f0,
                e.f2,
                PrimitiveType.INT_TYPE);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(TimesExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this,
                                                         env,
                e.f0,
                e.f2,
                                                         PrimitiveType.INT_TYPE);
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(AndExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this,
                env,
                e.f0,
                e.f2,
                PrimitiveType.BOOL_TYPE);
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(CompareExpression e, Environment env)
    {
        // get type of both sides
        TypeCheckUtil.TypeCheckBinaryArithLogExpression(this,
                env,
                e.f0,
                e.f2,
                PrimitiveType.INT_TYPE);
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(NotExpression n, Environment env)
    {
        Type op_type = n.f1.accept(this, env);
        if (op_type == null || !op_type.equals(PrimitiveType.BOOL_TYPE))
        {
            TypeError.close("! expression applied to non-bool");
        }
        return PrimitiveType.BOOL_TYPE;
    }

    public Type visit(ThisExpression t, Environment env)
    {
        // Get scoping method
        MethodType curr_method = (MethodType)((ScopedEnvironment)env).getScope();
        // return enclosing class
        return curr_method.getScope();
    }

    public Type visit (AllocationExpression alloc, Environment env) {
        String class_name = EnvironmentUtil.identifierToString(alloc.f1);
        // make sure the RHS is valid class name
        ClassType rhs = env.getGlobalEnvironment().getClass(class_name);
        if (null == rhs) {
            analysis.TypeError.close("Undeclared identifer" + class_name);
        }
        return rhs;
    }

    // Statement type checks
    public Type visit(AssignmentStatement a, Environment env)
    {

        Type lhs_type = a.f0.accept(this, env);
        Type rhs_type = a.f2.accept(this, env);

        if (null == lhs_type || null == rhs_type)
        {
            if (null== lhs_type)
            {
                TypeError.close("Undeclared identifier " + EnvironmentUtil.identifierToString(a.f0));
            }
            if (null== rhs_type)
            {
                TypeError.close("Type mismatch");
            }

        }

        if (!rhs_type.subtype(lhs_type))
        {
            TypeError.close("Type mismatch" + lhs_type.typeName() + ", " + rhs_type.typeName());
        }
        return lhs_type;
    }

    public Type visit(WhileStatement w, Environment env)
    {
        Type cond_type = w.f2.accept(this, env);
        if (cond_type != PrimitiveType.BOOL_TYPE)
        {
            TypeError.close("While loop condition must be of type bool");
        }
        // type check the rest
        w.f4.accept(this, env);
        return null;
    }

    public Type visit(IfStatement i, Environment env)
    {
        Type cond_type = i.f2.accept(this, env);
        if (cond_type != PrimitiveType.BOOL_TYPE)
        {
            TypeError.close("if condition must be of type bool");
        }
        // type check the rest
        i.f4.accept(this, env);
        i.f6.accept(this, env);
        return null;
    }

    public Type visit(PrintStatement p, Environment env)
    {
        if (PrimitiveType.INT_TYPE != p.f2.accept(this, env))
        {
            analysis.TypeError.close("println expects parameter type 'int'");
        }
        return null;
    }

    // Array type checkers

    public Type visit(ArrayAssignmentStatement a, Environment env)
    {
        Type index_type = a.f2.accept(this, env);
        Type rhs_type = a.f5.accept(this, env);
        if (null == index_type || !index_type.equals(PrimitiveType.INT_TYPE) || !rhs_type.equals(PrimitiveType.INT_TYPE))
        {
            TypeError.close("Array assignemtn malformed");
        }
        return null;
    }

    public Type visit(ArrayAllocationExpression e, Environment env)
    {
        Type len_type = e.f3.accept(this, env);
        if (PrimitiveType.INT_TYPE != len_type)
        {
            TypeError.close("Array allocation requires int");
        }
        return new IntArrayType();
    }

    public Type visit(ArrayLength l, Environment env)
    {
        Type lhs_type = l.f0.accept(this, env);
        if (null == lhs_type || !(lhs_type instanceof IntArrayType))
        {
            TypeError.close("Calling array length on not an array");
        }
        return PrimitiveType.INT_TYPE;
    }

    public Type visit(ArrayLookup l, Environment env)
    {
        Type array_type = l.f0.accept(this, env);
        Type index_type = l.f2.accept(this, env);
        if ((null == array_type || null == index_type) ||
            !(array_type instanceof IntArrayType) || null == index_type || !index_type.subtype(PrimitiveType.INT_TYPE))
        {
            TypeError.close("Array lookup perfomed on not an array or with wrong index");
        }
        return PrimitiveType.INT_TYPE;
    }

    // Literal type checks
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


    // Procedural Pass Throughs
    public Type visit(PrimaryExpression p, Environment env) {
        return p.f0.accept(this, env);
    }

    public Type visit(BracketExpression n, Environment env) {
        return n.f1.accept(this, env);
    }


}


