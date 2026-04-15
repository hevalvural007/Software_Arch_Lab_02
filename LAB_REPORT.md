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

### Architectural Comparison: Layered Architecture vs. Modular Monolith

Throughout Lab 01 and Lab 02, we transitioned from a Traditional Layered Architecture to a Modular Monolith. Understanding the differences between these two approaches highlights the importance of domain boundaries and information hiding.

* **Traditional Layered Architecture (Horizontal Slicing):**
  * **Structure:** The codebase is organized horizontally based on technical concerns (e.g., Controllers, Services, Repositories).
  * **Coupling:** It tends to have high cohesion technically but leads to **tight coupling** between business domains. For instance, in Lab 01, the `OrderService` could freely access the `ProductRepository` because both were public or resided in overlapping technical layers.
  * **Drawbacks:** As the system grows, business logic becomes scattered across layers. A change in a single business feature (like "Orders") requires modifying files across multiple horizontal layers, increasing the risk of unintended side effects.

* **Modular Monolith Architecture (Vertical Slicing):**
  * **Structure:** The codebase is organized vertically based on business domains (e.g., the `catalog` module and the `orders` module). Each module encapsulates its own technical layers (its own controllers, services, and repositories).
  * **Coupling:** It promotes **loose coupling** between domains and **high cohesion** within a domain. Modules act like mini-applications.
  * **Information Hiding:** By using `package-private` access modifiers and Factory patterns, a module hides its internal database and business logic from the rest of the system.
  * **Benefits:** Modules communicate exclusively through explicitly defined public APIs (Interfaces). In this lab, the `orders` module could only interact with the `catalog` module through the `CatalogService` interface, preventing it from directly manipulating product data. This makes the system much easier to maintain, test, and eventually split into microservices if needed in the future.

**Summary of the Transition:**
The shift to a Modular Monolith solved the architectural violation present in Lab 01. It prevented domain leakage by enforcing strict boundaries, ensuring that the Orders context respects the Catalog context's sovereignty over its own data.

### Conclusion and Evaluation
The given assignment has been successfully completed, migrating the system from a horizontally layered architecture to a vertical modular monolith. By utilizing `package-private` access modifiers and the Factory design pattern, tight coupling between domains was eliminated. The Orders module now interacts with the Catalog module exclusively through the `CatalogService` interface, successfully achieving Information Hiding and resulting in a highly cohesive, loosely coupled, and maintainable codebase.