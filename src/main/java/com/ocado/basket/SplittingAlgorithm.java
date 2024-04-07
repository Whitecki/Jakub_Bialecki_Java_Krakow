package com.ocado.basket;

import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.*;

public class SplittingAlgorithm {

    public HashMap<String, List<String>> findOptimalSupplierAssignment(List<String> items, List<Supplier> suppliers, Map<Product, Set<Supplier>> productSuppliersMap) {
        int supplierCount = suppliers.size();
        int maximumProductsDelivered = 0;
        List<Supplier> optimalSuppliers = new ArrayList<>();
        int optimalSupplierIndex = -1;

        for (int combinationSize = 1; combinationSize <= supplierCount && optimalSupplierIndex == -1; combinationSize++) {
            Iterator<int[]> combinationIterator = CombinatoricsUtils.combinationsIterator(supplierCount, combinationSize);
            while (combinationIterator.hasNext()) {
                int[] combination = combinationIterator.next();
                List<Supplier> currentSuppliers = getSuppliersByCombination(suppliers, combination);

                DeliveryCapabilityChecker.SupplierDeliveryInfo deliveryInfo = DeliveryCapabilityChecker.findMostCapableSupplier(items, currentSuppliers, productSuppliersMap);
                if (deliveryInfo.getIndex() > -1 && deliveryInfo.getMaxDeliveries() > maximumProductsDelivered) {
                    maximumProductsDelivered = deliveryInfo.getMaxDeliveries();
                    optimalSuppliers = currentSuppliers;
                    optimalSupplierIndex = deliveryInfo.getIndex();
                }
            }
        }

        return SupplierProductAssigner.assignProductsToSuppliers(optimalSuppliers, optimalSupplierIndex, items, productSuppliersMap);
    }

    private List<Supplier> getSuppliersByCombination(List<Supplier> suppliers, int[] combination) {
        List<Supplier> result = new ArrayList<>();
        for (int index : combination) {
            result.add(suppliers.get(index));
        }
        return result;
    }
}
