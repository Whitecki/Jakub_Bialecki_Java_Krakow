package com.ocado.basket;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

class ProductSupplierLoaderTest {

    @Test
    void testReadJsonFile() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {

        String filePath = "C:\\Users\\elyjk\\Desktop\\studia\\Poszukiwanie Sta\u017Cu\\Ocado\\Jakub_Bialecki_Java_Krakow\\src\\main\\resources\\config.json";

        ProductSupplierLoader reader = new ProductSupplierLoader(filePath);
        Method method = ProductSupplierLoader.class.getDeclaredMethod("readJsonFile", String.class);
        method.setAccessible(true);

        Object response = method.invoke(reader, filePath);

        assertInstanceOf(Map.class, response);
        Map<String, List<String>> result;
        if (response instanceof Map<?, ?>) {
            result = (Map<String, List<String>>) response;
            assertNotNull(result);
            assertFalse(result.isEmpty());
        } else {
            fail("Metoda readJsonFile nie zwróciła Map<String, List<String>>");
        }
    }




}