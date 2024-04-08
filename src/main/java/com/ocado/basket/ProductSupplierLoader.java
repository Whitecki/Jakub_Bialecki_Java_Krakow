package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ProductSupplierLoader {

    private final String configFilePath;

    public ProductSupplierLoader(String absolutePathToConfigFile){
        this.configFilePath = absolutePathToConfigFile;
    }

    public Map<Product, Set<Supplier>> config() {
        try {
            Map<String, List<String>> productsMap = readJsonFile(configFilePath);
            return mapToProductsAndSuppliers(productsMap);
        } catch (IOException e) {
            throw new RuntimeException("Error reading config file", e);
        }
    }

    private Map<String, List<String>> readJsonFile(String filePath) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, List<String>>>(){}.getType();

        try (FileReader fileReader = new FileReader(filePath)) {
            return gson.fromJson(fileReader, type);
        }catch (FileNotFoundException e) {
            throw new RuntimeException("Nie znaleziono pliku konfiguracyjnego: " + filePath, e);
        } catch (IOException e) {
            throw new RuntimeException("Wystąpił problem podczas odczytu pliku konfiguracyjnego: " + filePath, e);
        }
    }

    private Map<Product, Set<Supplier>> mapToProductsAndSuppliers(Map<String, List<String>> productsMap) {
        Map<Product, Set<Supplier>> productSuppliers = new HashMap<>(1000);
        for (Map.Entry<String, List<String>> entry : productsMap.entrySet()) {
            Product product = new Product(entry.getKey());
            Set<Supplier> suppliers = createSuppliersSet(entry.getValue());
            productSuppliers.put(product, suppliers);
        }
        return productSuppliers;
    }

    private Set<Supplier> createSuppliersSet(List<String> supplierNames) {
        Set<Supplier> suppliers = new HashSet<>();
        for (String name : supplierNames) {
            suppliers.add(new Supplier(name));
        }
        return suppliers;
    }
}
