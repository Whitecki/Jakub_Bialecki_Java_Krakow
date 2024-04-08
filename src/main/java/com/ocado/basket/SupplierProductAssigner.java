package com.ocado.basket;

import java.util.*;

/**
 * The SupplierProductAssigner class assigns products to suppliers based on the provided preferences and availability.
 * It returns a mapping of supplier names to the list of products they can deliver.
 */
public class SupplierProductAssigner {

    /**
     * Assigns products to suppliers with a preference for a particular supplier.
     *
     * @param suppliers A list of all suppliers.
     * @param preferredSupplierIndex The index of the preferred supplier in the suppliers list.
     * @param productNames Names of the products to be assigned to suppliers.
     * @param productSuppliersMap A map linking each product to a set of suppliers that can deliver it.
     * @return A map of supplier names to the list of product names they can deliver.
     */
    public static HashMap<String, List<String>> assignProductsToSuppliers(
            List<Supplier> suppliers,
            int preferredSupplierIndex,
            List<String> productNames,
            Map<Product, Set<Supplier>> productSuppliersMap) {

        HashMap<String, List<String>> assignment = new HashMap<>();
        Supplier preferredSupplier = suppliers.get(preferredSupplierIndex);

        // Initialize the list of products for each supplier
        for (Supplier supplier : suppliers) {
            assignment.put(supplier.name(), new ArrayList<>());
        }

        for (String productName : productNames) {
            Product product = new Product(productName);
            Set<Supplier> availableSuppliers = productSuppliersMap.getOrDefault(product, Collections.emptySet());
            availableSuppliers.retainAll(suppliers);

            // Giving preference to the chosen supplier
            if (availableSuppliers.contains(preferredSupplier)) {
                assignment.get(preferredSupplier.name()).add(product.name());
            } else {
                // Assigning the product to another available supplier
                for (Supplier supplier : availableSuppliers) {
                    assignment.get(supplier.name()).add(product.name());
                    break; // Assign to the first available supplier
                }
            }
        }

        return assignment;
    }
}
