# Codestyle of Konna

## Naming convention

The project follows the naming convention described below:

* All names classes (except internal), annotations and interfaces must begin with "K". The next letter after is also must be capital (example below). The rest of the name must follow PascalCase.
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
* Enumeration declarations follow PascalCase, however, Enumeration members follow SCREAMING_CASE
* Variables follow camelCase
* Constants (not final fields) follow SCREAMING_CASE

other info is to be written