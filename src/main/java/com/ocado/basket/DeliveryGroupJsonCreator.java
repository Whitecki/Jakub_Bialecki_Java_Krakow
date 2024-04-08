package com.ocado.basket;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeliveryGroupJsonCreator {

    public static String createDeliveryGroupsJson(HashMap<Supplier, List<Product>> supplierProductMap) {
        Map<String, List<String>> deliveryGroups = new HashMap<>();

        for (Map.Entry<Supplier, List<Product>> entry : supplierProductMap.entrySet()) {
            String deliveryMethod = entry.getKey().name();
            List<String> productNames = entry.getValue().stream()
                    .map(Product::name)
                    .collect(Collectors.toList());

            if (!deliveryGroups.containsKey(deliveryMethod)) {
                deliveryGroups.put(deliveryMethod, productNames);
            } else {
                deliveryGroups.get(deliveryMethod).addAll(productNames);
            }
        }

        return new Gson().toJson(deliveryGroups);
    }
}
