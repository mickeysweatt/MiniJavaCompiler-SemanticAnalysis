/**
 * Created by admin on 10/23/14.
 */

import environment.EnvironmentBuilderVisitor;
import environment.Environment;
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
            // first get all the class names
            EnvironmentBuilderVisitor v = new EnvironmentBuilderVisitor();

            GlobalEnvironment env = new GlobalEnvironment();
            g.accept(v, env);
            env.getClasses();

        } catch (ParseException e){
            System.out.println(e.toString());
        }
    }
}

