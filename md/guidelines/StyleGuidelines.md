# Style guidelines

1. **[Introduction](#introduction)**
2. **[Copyright notice](#copyright-notice)**
3. **[Classes](#Classes)**
4. **[Functions](#Functions)**
5. **[Global variables](#Global-variables)**
6. **[Flags](#Flags)**

## Introduction

To ensure the maintainability of this project, we designed a list of style guidelines.
Please ensure to read and apply them to your code, before contributing to the project.
Commits and pull requests not following these guidelines, will not be accepted.

## Copyright notice

Each file containing code must include a copyright notice at the top including all authors.

```java
/**
 *  /path/file
 *  
 *  Description of the functionality  of the file
 *  
 *  Copyright (C) YEAR name sirname mailadress
 */
```

## Classes

Each class should have a Javadoc comment explaining the functionality of the class.
Class names and each new word within the name have to start with a capital letter.
Underscore and minus are not allowed within class names. Logical spacing should be applied within the class.

```java
/**
* This class is does some stuff.
*/
public class SomeClass {}
```

## Functions

Each function has to have a Javadoc comment explaining the use of it and all parameters the function accepts. If the function has a return type, it should also be explained what it represents.
Function names should start with a small letter and every new word within the name with a capital one. Underscore and minus are not allowed within the name. The same applies to every parameter and variable name within the function. Logical spacing should be applied within the function. Also each variable within the function should have a comment above it, explaining its use.

```java
/**
* Checks if an int is odd.
*
* @param valueToCheck Int to check
*
* @return Boolean that represents if the int is odd
*/
public static boolean isOdd(int valueToCheck) {
if (valueToCheck % 2 != 0) return true;
return false;
}
```

## Global variables

Each global variable should have a Javadoc comment explaining its use. Global variable names should start with a small letter and every new word within the name with a capital one. Underscore and minus are not allowed within the name.

```java
/**
* This variable is used to store some int.
*/
public static int someVariable = 0;
```

## Flags

Each flag should have a Javadoc comment explaining its use. Flag names should be written in all capital letters and have an underscore between every new word within the name.

```java
/**
* This flag represents something.
*/
public static boolean SOME_FLAG = false;
```