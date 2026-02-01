# Codestyle of Konna

## ⚠️ Warning! ⚠️

All the statements listed below are not final and may change in the future depending
on many aspects that might be unforeseen

## Source file basis
* Encoding of all source files must be only UTF-8
* Indentation uses _spaces_ (not tabs)
* All lines must be no more than or equal to 100 characters in length

## Source file structure
A source file consists of the following, in this exact order:

* License
* Package statement
* Import statements
* Exactly one top-level class

The license is located at the very top of the file and contains this content:
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

## Codestyle basis

* Most of required codestyle statements can be read from [this file](config/checkstyle/checkstyle.xml), that is
the config of Checkstyle tool
* So all Checkstyle rules must be applied to all added code
* It is based mostly of Sun conventions but also adapted according to some things. See the file for more information

## Naming convention

* All names of classes (except internal), annotations and interfaces must begin with "K". The rest of the name use PascalCase (without separators)
```java
class Foo { // incorrect
}

class KfooBar { // incorrect
}

class KFooBar { // correct!
}

```
* All abbreviations in names should contain only the first letter as capital
```java
class KFooAPI { // incorrect
}

class KFooApi { // correct!
}
```
* Enumeration named use PascalCase. Enumeration members, however, use SCREAMING_CASE
* Variables, fields and method parameters use camelCase
* Constants SCREAMING_CASE. Especially if it is a static final field. However, regular field names use camelCase instead.
* Static fields use camelCase if they are not final
* Setters should start their name with *set*. Getters, on the other hand, should not start with *get* if the property is readonly, but it is not mandatory. Else it should.
* The setter naming rule may not cover libfrontends in order to keep compatibility

## Indentation, braces and line breaks

* Indentation of the code is 4 space symbols, *not tab characters*
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

## Other statements
* case-block in switch should be wrapped in braces:
```java
public class Main {
    public static void main(String[] args) {
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
* All methods parameters of non-primitive types should be final
* Magic numbers are not allowed
## Commit messages

* The first line of a message must match:
```
[#<issue>] <keyword>: <details>
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
* Only commits with keywords sec, feat, fix and ref are included in the changelog
* Detailed commit message is not mandatory
