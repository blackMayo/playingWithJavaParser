package com.tutorials.javacoding.hibernate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.google.common.base.Strings;
import com.tutorials.javacoding.hibernate.config.Functional;
import org.reflections.Reflections;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("load context");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Employee em = new Employee();
//        em.setId("123");
//        em.setName("John");
//        em.setAge(35);
//        EmployeeService emService = (EmployeeService) context.getBean("employeeService");
//        emService.persistEmployee(em);
//        System.out.println("Updated age :" + emService.findEmployeeById("123").getAge());
//        em.setAge(32);
//        emService.updateEmployee(em);
//        System.out.println("Updated age :" + emService.findEmployeeById("123").getAge());
//        emService.deleteEmployee(em);

        /*Properties props = new Properties();
        props.load(App.class.getClassLoader().getResourceAsStream("project.properties"));
        String basedir = (String) props.get("project.basedir");

        List<Class> annotatedClazzes = new ArrayList<Class>();

        for (Package pkg : Package.getPackages()) {
            Reflections reflections = new Reflections(pkg.getName());

            Set<Class<?>> allClasses =
                    reflections.getSubTypesOf(Object.class);

            for (Class<?> clazz : allClasses) {
                if (clazz.isAnnotationPresent(Functional.class)) {
                    annotatedClazzes.add(clazz);
                }
            }
        }*/


        File dir = new File("src/main/java");
        displayDirectoryContents(dir);
        context.close();
    }

    /**
     * http://www.avajava.com/tutorials/lessons/how-do-i-recursively-display-all-files-and-directories-in-a-directory.html
     *
     * @param dir the directory
     */
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