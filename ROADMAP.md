# Konna roadmap

## ⚠️ Warning! ⚠️

The roadmap is not final and may change in the future depending
on many aspects that might be unforeseen. Also, this may not cover features post-1.0.0 and
version names may be different in the future

## The roadmap

### v. 0.1.0 - JSON and UniversalMap
The foundation that is essential for the whole engine

- [x] Implement standard JSON Serializer and Deserializer
- [x] Setup test pipeline with JUnit and GitHub Actions
- [x] Add annotations that would be used to guide JSON serializer and deserializer
- [x] Implement UniversalMap - a universal container for data that don't need to be serialized to JSON

### v. 0.2.0 - The Base Core

- [x] Add classes that describe engine components and engine hypervisor
    - [x] Implement Engine component loader
    - [x] Add support for Engine component Api
      - [x] Add services - group of component endpoints
      - [x] Add clerks - util classes (aka "real services") for services
      - [x] Add necessary annotations that helps hypervisor to configure endpoint routing automatically
- [x] Add json schema validators
- [x] Implement methods for inter-component messaging
    - [x] Add Event system
    - [x] Add base for tunnels - message translators to help components understand each other
    - [x] Add translating Java AWT events to Konna events 
- [x] Add app class - Konna
  - [x] Add feature to select classes of engine components to be used in engine
  - [x] Add exception handling
- [x] Add object model - KObject
  - [x] Add Activator - wrapper for creating/"deleting" objects
  - [x] Add annotations to define ways to create an object (poolable, singleton etc.)
- [x] Add Logging
  - [x] Implement simple logger class that allows to write logs to stdout or a file
- [x] Add asset and resource management
  - [x] Add abstractions for assets and resources 
  - [x] Add asset loader
  - [x] Allow components to load their components by themselves
  - [x] Add post-init asset loading (would be helpful for modloaders)
  - [x] Add feature to define assets in json
- [x] Implement container system
  - [x] Add interface-implementation registration system
  - [x] Add getting interface implementation by type method
  - [x] Manage to make up automatic dependency injection
  - [x] Add system to configure dependencies from code
  - [x] Add automatic dependency injection in the Activator
- [x] Make everything concurrent and make the system work in different threads

### v. 0.3.0 - Graphics

- [ ] Crash logging by adding mandatory tag for log messages
- [ ] Add modules that represent frontends to different native libraries (libfrontend)
  - [ ] Add libfrontend for OpenGL 2.0 and 3.3
  - [ ] Add libfrontend for Dear ImGui
  - [ ] Add libfrontend for GLFW
  - [ ] Add libfrontend for STBImage
  - [ ] Maybe add all other required libfrontends
- [ ] Add modules that provide implementations of libfrontends (backend)
  - [ ] Add backend for OpenGL 2.0 and 3.3 using LWJGL
  - [ ] Add backend for Dear ImGui using LWJGL
  - [ ] Add backend for GLFW using LWJGL
  - [ ] Add backend for STBImage using LWJGL
  - [ ] Maybe add all other required backends using LWJGL and the like
- [ ] Implement Graphics component
  - [ ] Implement services to render different objects
  - [ ] Add abstractions for render primitives like shapes, textures, text etc.
  - [ ] Implement rendering...
    - [ ] ...of shapes
    - [ ] ...of textures
    - [ ] ...of text
    - [ ] ...of tiled text
  - [ ] Add support for rendering custom object, defined by user
  - [ ] Implement Graphics component's render frontend (that actually renders) using OpenGL 3.3
- [ ] Add support for ImGUI for everyone
  - [ ] Add interface for ImGUI that is independent of any existing ImGUI library and add it to engine context
  - [ ] Add implementation of the interface using Dear ImGui

### v. 0.4.0 - The beginning of Entity

- [ ] Implement entity component that contains methods to work with different entities, including the Player itself
    - [ ] Add base class for all entities
    - [ ] Add classes that holds different values that represent entity properties
    - [ ] Add feature to define entities in json files (so component will create them automatically)
    - [ ] Add feature to treat entity descriptors (aka definitions) as assets
    - [ ] Add support for entity behaviours - it will be just like script system
    - [ ] Add entity model - entity class with list of properties to concrete class with concrete fields (?)

### v. 0.5.0 - The beginning of Level

### v. 0.6.0 - Monsters and Combat

### v. 0.7.0 - Item system

### v. 0.8.0 - Kreaktive - the GUI component

### v. 0.9.0 - Saving

### v. 1.0.0 - To be continued...