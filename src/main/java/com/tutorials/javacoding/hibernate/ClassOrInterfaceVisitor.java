package com.tutorials.javacoding.hibernate;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author Ioanna Vletsou
 *         Copyright (c) 2016. All rights reserved.
 */
public class ClassOrInterfaceVisitor extends VoidVisitorAdapter {

    @Override
    public void visit(ClassOrInterfaceDeclaration dec, Object arg) {

        if (null != dec.getComment())
            System.out.println(dec.getName() + " " + dec.getComment().getContent());
    }
}
