# LAB 02 Report

## Modular Monolith Architecture Implementation

In this laboratory assignment, we refactored a **Modular Monolith** using pure Java. The main objective of the project is to divide the system into vertical business domains (modules) to enforce **Information Hiding** and ensure that modules communicate strictly through public **Interfaces (APIs)**, rather than sharing internal databases or implementations.

### System Architecture and Modules

The project is divided into two vertical business domains (modules) based on the Modular Monolith principles:

* **Catalog Module (`catalog` package):** Responsible for managing products and stock operations. It encapsulates its internal logic and exposes only what is necessary.
    * `CatalogService.java`: The `public` interface acting as the sole API exposed to other modules.
    * `CatalogFactory.java`: The `public` factory class responsible for instantiating the module's internal components (repository and service implementation) and returning only the interface.
    * `Product.java`, `ProductRepository.java`, `CatalogServiceImpl.java`: Internal domain, persistence, and implementation classes. These are strictly marked as `package-private` to hide them from the outside world.
* **Orders Module (`orders` package):** Manages the order placement process. It has no direct knowledge of the Catalog's internal structures (like `Product` or `ProductRepository`).
    * `OrderController.java`: The `public` entry point for the Orders context that handles user requests.
    * `OrdersFactory.java`: The `public` factory that wires the internal dependencies of the Orders module and requires the external `CatalogService` API to function.
    * `Order.java`, `OrderRepository.java`, `OrderService.java`: Internal domain, persistence, and business logic classes kept strictly `package-private`.
* **Entry Point:**
    * `Main.java`: The main bootstrapping class where the application is launched. It utilizes the module Factories to instantiate the system, demonstrating how the `Catalog` module is passed to the `Orders` module without exposing any internal implementations.

### Conclusion and Evaluation
The given assignment has been successfully completed, migrating the system from a horizontally layered architecture to a vertical modular monolith. By utilizing `package-private` access modifiers and the Factory design pattern, tight coupling between domains was eliminated. The Orders module now interacts with the Catalog module exclusively through the `CatalogService` interface, successfully achieving Information Hiding and resulting in a highly cohesive, loosely coupled, and maintainable codebase.