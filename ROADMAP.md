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
- [ ] Add json schema validators (?)
- [ ] Add wrapper class for Java AWT Window
  - [ ] Add abstraction for renderable object that holds primitive information such as color, image etc.
  - [ ] Add geometrical primitives classes
  - [ ] Add methods for window wrapper to draw renderable objects
- [ ] Implement methods for inter-component messaging
    - [ ] Add Event system
    - [ ] Add base for tunnels - message translators to help components understand each other
    - [ ] Add translating Java AWT events to Konna events 
- [ ] Add app class - Konna
  - [ ] Add feature to select classes of engine components to be used in engine
  - [ ] Add exception handling
- [x] Add object model - KObject
  - [x] Add Activator - wrapper for creating/"deleting" objects
  - [x] Add annotations to define ways to create an object (poolable, singleton etc.)
- [x] Add Logging
  - [x] Implement simple logger class that allows to write logs to stdout or a file
- [ ] Add asset and resource (?) management
  - [ ] Add abstractions for assets and resources 
  - [ ] Add asset loader
  - [ ] Allow components to load their components by themselves
  - [ ] Add post-init asset loading (would be helpful for modloaders)
  - [ ] Add feature to define assets in json
- [x] Implement container system
  - [x] Add interface-implementation registration system
  - [x] Add getting interface implementation by type method
  - [x] Manage to make up automatic dependency injection
  - [x] Add system to configure dependencies from code
  - [x] Add automatic dependency injection in the Activator
- [ ] Make everything concurrent and make the system work in different threads

### v. 0.3.0 - The beginning of Entity

- [ ] Implement entity component that contains methods to work with different entities, including the Player itself
    - [ ] Add base class for all entities
    - [ ] Add classes that holds different values that represent entity properties
    - [ ] Add feature to define entities in json files (so component will create them automatically)
    - [ ] Add feature to treat entity descriptors (aka definitions) as assets
    - [ ] Add support for entity behaviours - it will be just like script system
    - [ ] Add entity model - entity class with list of properties to concrete class with concrete fields (?)

### v. 0.4.0 - The beginning of Level

### v. 0.5.0 - Monsters and Combat

### v. 0.6.0 - Item system

### v. 0.7.0 - Kreaktive - the GUI component

### v. 0.8.0 - Saving

### v. 0.9.0 - To be continued...