package com.ocado.basket;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasketSplitterTest {


    @Test
    void testOptimalCourierSelection() throws IOException, ItemNotFoundException {
        // Arrange
        String jsonConfig = """
    {
        "Carrots (1kg)": ["Express Delivery", "Click&Collect"],
        "Cold Beer (330ml)": ["Express Delivery"],
        "Steak (300g)": ["Express Delivery", "Click&Collect"],
        "AA Battery (4 Pcs.)": ["Express Delivery", "Courier"],
        "Espresso Machine": ["Express Delivery", "Click&Collect"],
        "Garden Chair": ["Express Delivery"]
    }
    """;

        Path configPath = Files.createTempFile("configTest", ".json");
        Files.write(configPath, jsonConfig.getBytes());
        LinkedList<String> orderList = new LinkedList<>(Arrays.asList("Carrots (1kg)", "Cold Beer (330ml)"));

        // Act
        BasketSplitter splitter = new BasketSplitter(configPath.toString());
        var resultBasket = splitter.split(orderList);

        // Assert
        assertEquals(2, getMaxPackageSize(resultBasket));
    }

    @Test
    void testIllegalArgumentExceptionForEmptyItemList() throws IOException {
        // Arrange
        String configJson = """
    {
        "Carrots (1kg)": ["Express Delivery", "Click&Collect"]
    }
    """;

        Path configFilePath = Files.createTempFile("configTest", ".json");
        Files.write(configFilePath, configJson.getBytes());
        LinkedList<String> emptyList = new LinkedList<>();

        // Act
        BasketSplitter splitter = new BasketSplitter(configFilePath.toString());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> splitter.split(emptyList), "The list of items cannot be null or empty.");
    }

    @Test
    void testCourierCapacityEvaluation() throws IOException, ItemNotFoundException {
        // Arrange
        String configJsonString = """
    {
        "Carrots (1kg)": ["Express Delivery", "Click&Collect"],
        "Cold Beer (330ml)": ["Express Delivery"],
        "Steak (300g)": ["Express Delivery", "Click&Collect"],
        "AA Battery (4 Pcs.)": ["Courier"],
        "Espresso Machine": ["Click&Collect"],
        "Garden Chair": ["Courier", "Click&Collect"]
    }
    """;
        Path temporaryConfigFile = Files.createTempFile("configTest", ".json");
        Files.write(temporaryConfigFile, configJsonString.getBytes());
        LinkedList<String> productBasket = new LinkedList<>(Arrays.asList("Carrots (1kg)", "Cold Beer (330ml)", "Steak (300g)", "AA Battery (4 Pcs.)", "Espresso Machine", "Garden Chair"));

        // Act
        BasketSplitter myBasketSplitter = new BasketSplitter(temporaryConfigFile.toString());
        var dividedBasket = myBasketSplitter.split(productBasket);

        // Assert
        assertEquals(4, getMaxPackageSize(dividedBasket));
        assertEquals(3, getTotalNumberOfCouriers(dividedBasket));
    }

    @Test
    void testItemNotFoundExceptionForMissingItems() throws IOException {
        // Arrange
        String configData= """
    {
        "Carrots (1kg)": ["Express Delivery", "Click&Collect"]
    }
    """;

        Path configFile = Files.createTempFile("configTest", ".json");
        Files.write(configFile, configData.getBytes());

        List<String> shoppingBasket = Arrays.asList("Carrots (1kg)", "Unknown Item");
        BasketSplitter splitterInstance = new BasketSplitter(configFile.toString());

        // Act and Assert
        String missingItemMessage = "Item not found in product suppliers: Unknown Item";
        ItemNotFoundException thrownException = assertThrows(ItemNotFoundException.class, () -> splitterInstance.split(shoppingBasket));
        assertEquals(missingItemMessage, thrownException.getMessage());
    }

    private int getTotalNumberOfCouriers(Map<String, List<String>> splitResult) {
        return splitResult.size();
    }

    private int getMaxPackageSize(Map<String, List<String>> resultBasket) {
        return resultBasket.values().stream().mapToInt(List::size).max().orElse(0);
    }

}