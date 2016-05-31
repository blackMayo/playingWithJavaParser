package com.tutorials.javacoding.hibernate;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * @author Ioanna Vletsou
 *         Copyright (c) 2016. All rights reserved.
 */
public class MethodVisitor extends VoidVisitorAdapter {

    @Override
    public void visit(MethodDeclaration dec, Object arg) {
        System.out.println(dec.getName() + "\n");
        System.out.println(dec.getComment().getContent());
    }
}
