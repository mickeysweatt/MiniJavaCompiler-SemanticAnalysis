/**
 * Created by admin on 10/23/14.
 */

import analysis.TypeCheckVisitor;
import environment.EnvironmentBuilderVisitor;
import environment.GlobalEnvironment;
import  syntaxtree.*;
import parser.MiniJavaParser;
import parser.ParseException;

import java.io.*;

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
            Goal g = parse.Goal();
            // First build up the envrionment (All classes, methods, and parameters for each method)
            EnvironmentBuilderVisitor e = new EnvironmentBuilderVisitor();
            GlobalEnvironment env = new GlobalEnvironment();
            g.accept(e, env);

            // then type check
            TypeCheckVisitor v = new TypeCheckVisitor();
            g.accept(v, env);


        } catch (ParseException e){
            System.out.println(e.toString());
        }
    }
}

