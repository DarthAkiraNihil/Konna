# Konna roadmap

## ⚠️ Warning! ⚠️

The roadmap is not final and may change in the future depending
on many aspects that might be unforeseen

## The roadmap

### v. 0.1.0 - JSON and UniversalDictionary
The foundation that is essential for the whole engine

- [ ] Implement standard JSON Serializer and Deserializer
- [ ] Setup test pipeline with JUnit and GitHub Actions
- [ ] Add annotations that would be used to guide JSON serializer and deserializer
- [ ] Implement UniversalDictionary - a universal container for data that don't need to be serialized to JSON

### v. 0.2.0 - The Base Core

- [ ] Add classes that describe engine components and engine hypervisor
    - [ ] Implement Engine components are to be able to be loaded from a .jar file
    - [ ] Implement Engine component loader
    - [ ] Add support for Engine component Api - methods that can be called from the hypervisor by name
- [ ] Add wrapper class for Java AWT Window
- [ ] Implement methods for inter-component messaging
    - [ ] Add Event system
    - [ ] Abb base for tunnels - Engine component wrappers that may be a dependency for other components 

### v. 0.3.0 - Dependency Injection

- [ ] Implement container system
    - [ ] Add interface-implementation registration system
    - [ ] Add getting interface implementation by type method
    - [ ] Manage to make up automatic dependency injection

### v. 0.4.0 - To be continued