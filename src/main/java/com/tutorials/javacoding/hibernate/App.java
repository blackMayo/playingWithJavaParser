package com.tutorials.javacoding.hibernate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("load context");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        File dir = new File("src/main/java");
        displayDirectoryContents(dir);
        context.close();
    }

    /**
     * http://www.avajava.com/tutorials/lessons/how-do-i-recursively-display-all-files-and-directories-in-a-directory.html
     *
     * @param dir the directory
     */
    @SuppressWarnings("unchecked")
    public static void displayDirectoryContents(File dir) {
        try {
            File[] files = dir.listFiles();
            if (null != files) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        displayDirectoryContents(file);
                    } else if (StringUtils.endsWithIgnoreCase(file.getName(), ".java")) {
                        FileInputStream in = new FileInputStream(file);

                        CompilationUnit cu = new CompilationUnit();
                        try {
                            cu = JavaParser.parse(in);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } finally {
                            in.close();
                        }
                        List<TypeDeclaration> types = cu.getTypes();
                        if (!CollectionUtils.isEmpty(types)) {
                            for (TypeDeclaration type : types) {
                                List<AnnotationExpr> annotations = type.getAnnotations();
                                if (!annotations.isEmpty()) {
                                    for (AnnotationExpr annotation : annotations) {
                                        if (annotation.getName().equals(new NameExpr("Functional"))) {
                                            ClassOrInterfaceVisitor visitor = new ClassOrInterfaceVisitor();
                                            visitor.visit(cu, null);
                                            MethodVisitor visitor2 = new MethodVisitor();
                                            visitor2.visit(cu, null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}