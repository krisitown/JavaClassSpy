package com.company;

import javax.lang.model.element.TypeParameterElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ClassSpy {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String className = scn.nextLine();
        try{
            //gets info about the class from its name
            Class<?> c = Class.forName(className);

            //gets the name of the class
            System.out.println("Class: " + c.getCanonicalName());

            //gets the modifiers of the class and then makes them a string for easier viewing
            System.out.println("\nModifiers: " + Modifier.toString(c.getModifiers()));

            System.out.println("\nType Parameters:");

            //gets all of the type variables of the class(if any)
            TypeVariable[] typeVariables = c.getTypeParameters();
            if(typeVariables.length != 0){
                for (TypeVariable typeVariable : typeVariables) {
                    System.out.print("-->");
                    System.out.println(typeVariable.getName());
                }
            }
            else {
                System.out.println("  --No Type Parameters--\n");
            }

            //gets all of the interfaces, which the class has implemented
            System.out.println("\nImplemented interfaces:");
            Type[] interfaces = c.getGenericInterfaces();
            if(interfaces.length != 0){
                for (Type currentInterface : interfaces) {
                    System.out.println("-->" + currentInterface.toString());
                }
            }

            //gets all parent(super) classes of the given class
            System.out.println("\nInheritance Path:");
            List<Class> superClasses = new ArrayList<>();
            getSuperClasses(superClasses, c);
            if(superClasses.size() != 0){
                String indentation = "-->";
                Collections.reverse(superClasses);
                for (Class superClass : superClasses) {
                    System.out.println(indentation + superClass.getCanonicalName());
                    indentation = "--" + indentation;
                }
            }
            else {
                System.out.println("  --No parent classes--  ");
            }

            //gets the annotations of the class(if any)
            System.out.println("\nAnnotations:");
            Annotation[] annotations = c.getAnnotations();
            if(annotations.length != 0){
                for (Annotation annotation : annotations) {
                    System.out.println("-->" + annotation.toString());
                }
            }
            else {
                System.out.println("  --No Annotations--  ");
            }
        }
        catch (ClassNotFoundException classNFException){
            classNFException.printStackTrace();
        }
    }

    public static void getSuperClasses(List<Class> list, Class givenClass) {
        Class<?> parent = givenClass.getSuperclass();
        if(parent != null){
            list.add(parent);
            getSuperClasses(list, parent);
        }
    }
}
