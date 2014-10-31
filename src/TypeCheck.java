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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class TypeCheck {
    public static void main(String[] Args) {
        File testDir = new File("tests");
        MiniJavaParser parse = null;
        for (final File fileEntry : testDir.listFiles()) {
            if (fileEntry.isFile()) {
                FileInputStream in = null;
                try {
                    in = new FileInputStream(fileEntry.getAbsoluteFile());
                }
                catch (FileNotFoundException err) {
                    err.printStackTrace();
                }

                try {
                    System.out.println("Processing: " + fileEntry.getName());
                    if (null == parse) {
                        parse = new MiniJavaParser(in);
                    }
                    else {
                        parse.ReInit(in);
                    }
                    Goal g = MiniJavaParser.Goal();
                    // First build up the environment (All classes, methods, and parameters for each method)
                    EnvironmentBuilderVisitor e = new EnvironmentBuilderVisitor();
                    GlobalEnvironment env = new GlobalEnvironment();
                    g.accept(e, env);
                    EnvironmentBuilderUtil.flattenSubtyping(env);

                    // then type check
                    try {
                        TypeCheckVisitor v = new TypeCheckVisitor();
                        g.accept(v, env);
                        System.out.println("Type checks\n");
                    } catch (Exception err) {
                        continue;
                    }
                }
                catch (ParseException e) {
                    System.out.println(e.toString());
                }
            }
        }
    }
}





