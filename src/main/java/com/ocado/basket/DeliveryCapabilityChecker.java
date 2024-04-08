package com.ocado.basket;

import java.util.*;

public class DeliveryCapabilityChecker {

    // Finds the most capable supplier for delivering all items.
    public static SupplierDeliveryInfo findMostCapableSupplier(List<String> items, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers) {
        List<Integer> deliveriesPerSupplier = initializeDeliveryCounts(suppliers.size());
        if (!canDeliverAllItems(items, suppliers, productSuppliers, deliveriesPerSupplier)) {
            return new SupplierDeliveryInfo(-1, 0, suppliers);
        }
        int index = findIndexOfMax(deliveriesPerSupplier);
        return new SupplierDeliveryInfo(index, deliveriesPerSupplier.get(index), suppliers);
    }

    // Initializes delivery count for each supplier.
    private static List<Integer> initializeDeliveryCounts(int size) {
        return new ArrayList<>(Collections.nCopies(size, 0));
    }

    // Checks if all items can be delivered by the available suppliers.
    private static boolean canDeliverAllItems(List<String> items, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers, List<Integer> deliveriesPerSupplier) {
        for (String item : items) {
            if (!updateDeliveryCountsForProduct(new Product(item), suppliers, productSuppliers, deliveriesPerSupplier)) {
                return false;
            }
        }
        return true;
    }

    // Updates the delivery count for each supplier based on a specific product.
    private static boolean updateDeliveryCountsForProduct(Product product, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliers, List<Integer> deliveriesPerSupplier) {
        Set<Supplier> availableSuppliers = productSuppliers.getOrDefault(product, Collections.emptySet());
        boolean isDeliverable = false;

        for (Supplier supplier : availableSuppliers) {
            int index = suppliers.indexOf(supplier);
            if (index >= 0) {
                deliveriesPerSupplier.set(index, deliveriesPerSupplier.get(index) + 1);
                isDeliverable = true;
            }
        }

        return isDeliverable;
//        return availableSuppliers.stream().anyMatch(supplier -> updateDeliveryCount(supplier, suppliers, deliveriesPerSupplier));
    }

    // Updates delivery count for a given supplier.
    private static boolean updateDeliveryCount(Supplier supplier, List<Supplier> suppliers, List<Integer> deliveriesPerSupplier) {
        int index = suppliers.indexOf(supplier);
        if (index >= 0) {
            deliveriesPerSupplier.set(index, deliveriesPerSupplier.get(index) + 1);
            return true;
        }
        return false;
    }

    // Finds the index of the maximum value in a list.
    private static int findIndexOfMax(List<Integer> list) {
        return list.indexOf(Collections.max(list));
    }

    // Holds supplier index, max deliveries, and a copy of the supplier list.
        record SupplierDeliveryInfo(int index, int maxDeliveries, List<Supplier> suppliers) {
            SupplierDeliveryInfo(int index, int maxDeliveries, List<Supplier> suppliers) {
                this.index = index;
                this.maxDeliveries = maxDeliveries;
                this.suppliers = new ArrayList<>(suppliers);
            }

            @Override
            public List<Supplier> suppliers() {
                return new ArrayList<>(suppliers);
            }
        }
}
