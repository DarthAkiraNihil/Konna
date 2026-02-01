# Konna
![Maven Central Version](https://img.shields.io/maven-central/v/io.github.darthakiranihil/konna.bom?style=plastic&label=BOM%20Version%20(Maven%20Central)&color=00aa88)

Yet another free and open-source game engine (that is possibly like a framework) for roguelike games.
It is the hobby project so far, but it may turn into something bigger

[Project Roadmap](ROADMAP.md)

## The core principles

### The Hyperpurism

The project should use as few dependencies as possible. A dependency is to be included only in case if it implements
functionality that would be too complex to implement an independent solution (such as byte-code manipulation, code generation)

### The Hypertuningability

The engine is not for all types of games. It is not universal in that aspect. However, it could be easily modified with engine components that you can create by yourself

### The Hyperindependency

No Engine component should know about other components. They should communicate with events, tunnels and messages

## Module structure
```
Konna/
├── core/ // Konna core module
├── konna-bom/ // Konna BOM
├── libfrontend/ // Libfrontend modules (often native library interfaces)
│   └── ...
├── backend/ // Backends - libfrontend implementations
│   └── ...
├── components/ // Standard Konna components
│   └── ...
└── ... // other source code
```

## Getting started

1. Install JDK 21. Adoptium (Temurin) is preferred but you can use another one that you like.
2. Be sure you use Maven Central in your project
    ```groovy
    repositories {
        mavenCentral()
    }
    ```
3. Use Konna platform in your dependencies and install required Konna modules
   ```groovy
   dependencies {
       implementation platform("io.github.darthakiranihil:konna.bom:0.3.0")
   
       implementation "io.github.darthakiranihil:konna.core" // Konna core. It is a required module!
      
       implementation "io.github.darthakiranihil:konna.component-graphics" 
       implementation "io.github.darthakiranihil:konna.backend-lwjgl" 
       implementation "io.github.darthakiranihil:konna.implementations-opengl33" 
       // Other modules and dependencies
   }
   ```

## Building from source

1. Install JDK 21. Adoptium (Temurin) is preferred but you can use another one that you like.
2. Clone this repository
    ```shell
    git clone https://github.com/DarthAkiraNihil/Konna.git
    ```
3. Change gradlew permissions (Linux/MacOS)
   ```shell
   chmod +x ./gradlew
   ```
4. Build Konna
   ```shell
   ./gradlew build 
   ```
5. Additionally, you can create Konna bundles
   ```shell
   ./gradlew createBundle
   ```
   As its result you will get three zip archives with jars, sources and docs for each module inside this project
P.S. You also can clone the project and open it with IntellijIDEA and it will do its initialization automatically
so you only have to run the build command (also IDEA is the preferred IDE to develop for Konna)

## Project status

![Repobeats analytics](https://repobeats.axiom.co/api/embed/e94e0d2b3510ebb7c4ce253f4a245d58039e9519.svg "Repobeats analytics image")

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for more information
