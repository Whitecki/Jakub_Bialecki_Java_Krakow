package com.ocado.basket;

import java.util.*;

public class BasketSplitter {

    // Map of products to their respective suppliers.
    private final Map<Product, Set<Supplier>> productSuppliers;

    // Constructor that loads product suppliers configuration from a file.
    public BasketSplitter(String configFilePath) {
        try {
            ProductSupplierLoader productSupplierLoader = new ProductSupplierLoader(configFilePath);
            this.productSuppliers = productSupplierLoader.config();
        } catch (RuntimeException e) {
            throw new IllegalStateException("Error loading configuration: " + configFilePath, e);
        }
    }

    // Splits items into groups based on the best matching suppliers.
    public Map<String, List<String>> split(List<String> items) throws ItemNotFoundException {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("The list of items cannot be null or empty.");
        }
        try {
            Set<Supplier> allSuppliers = aggregateSuppliersForItems(items);
            List<Supplier> supplierList = new ArrayList<>(allSuppliers);

            SplittingAlgorithm splittingAlgorithm = new SplittingAlgorithm();
            return splittingAlgorithm.findOptimalSupplierAssignment(items, supplierList, productSuppliers);
        } catch (Exception e) {
            throw new RuntimeException("Error processing the item list.", e);
        } catch (ItemNotFoundException e) {
            throw new ItemNotFoundException(e.getMessage());
        }
    }

    // Aggregates suppliers for the given list of items.
    private Set<Supplier> aggregateSuppliersForItems(List<String> items) throws ItemNotFoundException {
        Set<Supplier> suppliers = new HashSet<>();
        for (String item : items) {
            Product product = new Product(item);
            Set<Supplier> availableSuppliers = productSuppliers.get(product);

            if (availableSuppliers == null || availableSuppliers.isEmpty()) {
                throw new ItemNotFoundException("Item not found in product suppliers: " + item);
            }

            suppliers.addAll(availableSuppliers);
        }
        return suppliers;
    }
}
