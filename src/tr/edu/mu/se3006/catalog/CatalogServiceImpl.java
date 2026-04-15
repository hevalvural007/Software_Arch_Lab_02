package tr.edu.mu.se3006.catalog;

// Package-private implementation. Hidden from the outside world.
class CatalogServiceImpl implements CatalogService {
    
    // TODO: Define ProductRepository dependency
    ProductRepository repository;
    
    // TODO: Implement Constructor Injection

    public CatalogServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void checkAndReduceStock(Long productId, int quantity) {
        // TODO 1: Find product via repository
        Product product = repository.findById(productId);
        // TODO 2: Check stock (throw IllegalArgumentException if insufficient)
        if(product == null) {
            throw new IllegalArgumentException("Product not found!");
        }
        if(quantity > product.getStock()) {
            throw new IllegalArgumentException("Insufficient stock!");
        }
        // TODO 3: Reduce stock
        product.setStock(product.getStock() - quantity);
        // TODO 4: Save updated product
        repository.save(product);
    }
}
