package com.ocado.basket;

import java.util.*;

public class DeliveryCapabilityChecker {

    /**
     * Determines the index of the supplier in the provided list who can deliver the most items.
     * If all items cannot be delivered, returns -1.
     *
     * @param items The list of items to be delivered.
     * @param suppliers The list of available suppliers.
     * @param productSuppliers Mapping of products to their possible suppliers.
     * @return The index of the most capable supplier in the list, or -1 if delivery is not possible.
     */
    public static SupplierDeliveryInfo findMostCapableSupplier(List<String> items, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers) {
        List<Integer> deliveriesPerSupplier = initializeDeliveryCounts(suppliers.size());
        if (!canDeliverAllItems(items, suppliers, productSuppliers, deliveriesPerSupplier)) {
            return new SupplierDeliveryInfo(-1, 0, suppliers);
        }
        int index = findIndexOfMax(deliveriesPerSupplier);
        int maxDeliveries = deliveriesPerSupplier.get(index);
        return new SupplierDeliveryInfo(index, maxDeliveries, suppliers);
    }

    /**
     * Initializes a list with the delivery count set to 0 for each supplier.
     *
     * @param size The size of the supplier list.
     * @return A list with delivery counts initialized to 0.
     */
    private static List<Integer> initializeDeliveryCounts(int size) {
        return new ArrayList<>(Collections.nCopies(size, 0));
    }

    /**
     * Checks if all items can be delivered by the available suppliers.
     *
     * @param items The list of items to be delivered.
     * @param suppliers The list of suppliers.
     * @param productSuppliers Mapping of products to their possible suppliers.
     * @param deliveriesPerSupplier The list to track the number of deliveries per supplier.
     * @return true if all items can be delivered; false otherwise.
     */
    private static boolean canDeliverAllItems(List<String> items, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers, List<Integer> deliveriesPerSupplier) {
        for (String item : items) {
            Product product = new Product(item);
            if (!updateDeliveryCountsForProduct(product, suppliers, productSuppliers, deliveriesPerSupplier)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the delivery count for each supplier based on a specific product.
     *
     * @param product The product to check for delivery capability.
     * @param suppliers The list of suppliers.
     * @param productSuppliers Mapping of products to their possible suppliers.
     * @param deliveriesPerSupplier The list to track the number of deliveries per supplier.
     * @return true if at least one supplier can deliver the product; false otherwise.
     */
    private static boolean updateDeliveryCountsForProduct(Product product, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers, List<Integer> deliveriesPerSupplier) {
        boolean deliveryPossible = false;
        Set<Supplier> availableSuppliers = productSuppliers.getOrDefault(product, Collections.emptySet());

        for (int i = 0; i < suppliers.size(); i++) {
            if (availableSuppliers.contains(suppliers.get(i))) {
                deliveryPossible = true;
                deliveriesPerSupplier.set(i, deliveriesPerSupplier.get(i) + 1);
            }
        }

        return deliveryPossible;
    }

    /**
     * Finds the index of the maximum value in a list.
     *
     * @param list The list to search through.
     * @return The index of the maximum value.
     */
    private static int findIndexOfMax(List<Integer> list) {
        return list.indexOf(Collections.max(list));
    }

    static class SupplierDeliveryInfo {
        private final int index;
        private final int maxDeliveries;
        private final List<Supplier> suppliers;

        SupplierDeliveryInfo(int index, int maxDeliveries, List<Supplier> suppliers) {
            this.index = index;
            this.maxDeliveries = maxDeliveries;
            this.suppliers = new ArrayList<>(suppliers); // Tworzenie kopii listy
        }

        public int getIndex() {
            return index;
        }

        public int getMaxDeliveries() {
            return maxDeliveries;
        }

        public List<Supplier> getSuppliers() {
            return new ArrayList<>(suppliers); // Zwracanie kopii listy dla bezpieczeństwa
        }
    }
}
