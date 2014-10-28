/**
 * Created by admin on 10/23/14.
 */

import analysis.TypeCheckVisitor;
import environment.EnvironmentBuilderUtil;
import environment.EnvironmentBuilderVisitor;
import environment.GlobalEnvironment;
import parser.MiniJavaParser;
import parser.ParseException;
import syntaxtree.Goal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TypeCheck {
    public static void main(String[] Args ) {

        InputStream in = null;
        try {
            in = new FileInputStream("tests/test.java");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        MiniJavaParser parse = new MiniJavaParser(in);
        try {
            Goal g = MiniJavaParser.Goal();
            // First build up the environment (All classes, methods, and parameters for each method)
            EnvironmentBuilderVisitor e = new EnvironmentBuilderVisitor();
            GlobalEnvironment env = new GlobalEnvironment();
            g.accept(e, env);
            EnvironmentBuilderUtil.flattenSubtyping(env);

            // then type check
            TypeCheckVisitor v = new TypeCheckVisitor();
            g.accept(v, env);
            System.out.println("Type checks");

        } catch (ParseException e){
            System.out.println(e.toString());
        }
    }
}

