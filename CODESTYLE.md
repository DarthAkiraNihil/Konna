# Codestyle of Konna

## General statements

All the source code must fit Checkstyle rules, defined in [this file](config/checkstyle/checkstyle.xml). It is mostly based on standard
(Sun) Java Code Conventions with own modifications. Note that the rules may not be satisfied in some source files
due to the fact they are old.

## Source files
* Encoding of all source files must be only UTF-8
* Indentation uses 4 _spaces_ (not tabs)
* All lines must be no more than or equal to 100 characters in length
* A source file consists of the following, in this exact order:
  * License - standard license preamble for Apache 2.0. Copyright must start with year 2025. (see example at the end of the guide)
  * Package statement
  * Import statements
  * Exactly one top-level class

## Source code

### Naming convention

* Class names
  * All names of classes, records, enums, annotations and interfaces must begin with "K"
  * The rest of the name use PascalCase (without separators)
    ```java
    class Foo { // incorrect
    }

    class KfooBar { // incorrect
    }

    class KFooBar { // correct!
    }
    ```
  * Requirement of starting "K" may not be satisfied by nested classes
  * All abbreviations in names should contain only the first letter as capital
    ```java
    class KFooAPI { // incorrect
    }

    class KFooApi { // correct!
    }
    ```
  * Enumeration names use PascalCase. Enumeration members, however, use SCREAMING_CASE
* Variables and fields
  * Variables, fields and method parameters use camelCase
  * Constants SCREAMING_CASE. Especially if it is a `static final` field. However, regular field names use camelCase instead
  * Static fields use camelCase if they are not final
* Methods
  * Method names use camelCase
  * Getters can be named either starting from `get` (like `getX`) or just with the field name itself (like `x`)
    The second variant is preferred for readonly fields
  * Setters should start their name with `set`. This rule may not cover `libfrontend`s in order to keep compatibility

### Indentation, braces and line breaks

* The open curly brace *must* be on the same line for *everything* (methods, loops, ifs etc.) and be followed with a space before it
```cpp
if (condition) // incorrect
{
    
}

if (condition){ // incorrect

}

if (condition) { // correct!
    
}
```
* Always wrap expressions in braces. No exceptions!
```cpp
if (condition)  // incorrect
    method1();


if (condition) { // correct!
    method1();    
}
```
* If method parameter list is too long, chop it down!
```java
public void method(
    int a,
    int b,
    int c,
    final String d
    // ...
) {
    // ... 
}
```

### Other rules

* All method parameters of reference types must be `final`
* No redundant imports
* case-block in switch should be wrapped in braces:
```java
public class Main {
    static void main(String[] args) {
        int condition = 1;
        switch(condition) {
            case 1: {
                //...
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }
        }
    }
    
}
```
* default block of switch have to be included if and only if condition
variable does not belong to an enum, or it belongs to but cases does not cover all enum values
* Magic numbers are not allowed, except in `libfrontend`s, test classes, internal classes and some cases where
  adding a constant is pointless
* All rules are better to be followed in test classes, but it is not required there

## Commit messages

* The first line of a message must match:
```
[#<issue>] <keyword>[, <otherKeywords>]: <details>
```

Where the keyword is from the following list:

| Keyword | Description                                    |
|---------|------------------------------------------------|
| sec     | Security                                       |
| ci      | Working with CI                                |
| doc     | Documentation update                           |
| feat    | Adding new feature(s)                          |
| fix     | Bugfix                                         |
| ref     | Refactoring without changing the functionality |
| revert  | Reverting to previous commits                  |
| style   | Codestyle fixing                               |
| test    | Working with tests                             |
| dev     | Changes that do not fit any category above     |

* Only use multiple keywords if multiple aspects of editing changed (like, you fixed the codestyle by adding docs)
* Only commits with keywords sec, feat, fix and ref are included in the changelog
* Detailed commit message is not mandatory

## Example of license preamble
```java
/*
* Copyright 2025-present the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
  */
```
