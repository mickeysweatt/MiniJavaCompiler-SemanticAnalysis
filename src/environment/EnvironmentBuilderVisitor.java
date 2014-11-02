package environment;

import analysis.TypeError;
import syntaxtree.*;
import visitor.GJVoidDepthFirst;

import static analysis.TypeError.close;

/*
* Author: Mickey Sweatt
 */

public class EnvironmentBuilderVisitor extends GJVoidDepthFirst<GlobalEnvironment> {
    private ClassType m_currentClass;

    public void visit(Goal g, GlobalEnvironment env) {
        // goes through the first pass just to get the class names
        ClassNameVisitor v = new ClassNameVisitor();
        v.visit(g, env);
        super.visit(g, env);
    }

    public void visit(MainClass m, GlobalEnvironment env) {
        // add the arg list as a parameter, which cannot be accessed?
        String mainClassName = EnvironmentUtil.identifierToString(m.f1);
        String argsName = EnvironmentUtil.identifierToString(m.f11);
        ClassType main = env.getClass(mainClassName);
        MethodType mainMethod = new MethodType("main", null, main, null);
        mainMethod.addParameter(new VarType(null, argsName, main));
        main.addMethod(mainMethod);

        m_currentClass = main;
        super.visit(m, env);
    }

    // assumes all class names are in env
    public void visit(ClassDeclaration d, GlobalEnvironment env) {
        String class_name = EnvironmentUtil.classname(d);
        ClassType curr_class = env.getClass(class_name);
        EnvironmentBuilderUtil.addInstanceVariablesToClass(d.f3, curr_class, env);
        env.addClass(curr_class);
        m_currentClass = curr_class;
        super.visit(d, env);
    }

    public void visit(ClassExtendsDeclaration d, GlobalEnvironment env) {
        String class_name = EnvironmentUtil.classname(d);
        String super_name = EnvironmentUtil.identifierToString(d.f3);
        ClassType curr_class = env.getClass(class_name);
        ClassType super_class = env.getClass(super_name);
        if (null == super_class) {
            TypeError.close("Undeclared identifer " + super_name);
        }
        EnvironmentBuilderUtil.addInstanceVariablesToClass(d.f5, curr_class, env);
        curr_class.addSuperClass(super_class);
        env.addClass(curr_class);
        m_currentClass = curr_class;
        super.visit(d, env);
    }

    public void visit(MethodDeclaration m, GlobalEnvironment env) {
        EnvironmentBuilderUtil.addMethodToClass(m, m_currentClass, env);

        super.visit(m, env);
    }
}

// Just completes first pass to get the class names
class ClassNameVisitor extends GJVoidDepthFirst<GlobalEnvironment> {
    public void visit(Goal g, GlobalEnvironment env) {
        g.f0.accept(this, env);
        g.f1.accept(this, env);
    }

    public void visit(MainClass m, GlobalEnvironment env) {
        String class_name = EnvironmentUtil.classname(m);
        if (!env.containsEntry(class_name)) {
            env.addClass(new ClassType(class_name));
        } else {
            close("Redefining class " + class_name);
        }
        super.visit(m, env);
    }

    public void visit(TypeDeclaration d, GlobalEnvironment env) {
        String class_name = EnvironmentUtil.classname(d.f0.choice);
        if (env.containsEntry(class_name)) {
            close("Redefining class " + class_name);
        } else {
            env.addClass(new ClassType(class_name));
        }
        super.visit(d, env);
    }
}
