/**
 * Created by admin on 10/23/14.
 */

import analysis.EnvironmentBuilderVisitor;
import environment.Environment;
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

            Environment env = new Environment();
            g.accept(v, env);
            env.getClasses();

        } catch (ParseException e){
            System.out.println(e.toString());
        }
    }
}

