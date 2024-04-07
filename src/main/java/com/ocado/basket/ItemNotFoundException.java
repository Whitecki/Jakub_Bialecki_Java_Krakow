package com.ocado.basket;

public class ItemNotFoundException extends Throwable {

    String cos;
    public ItemNotFoundException(String s) {
        cos = s;
    }

    @Override
    public String getMessage(){
        return cos;
    }
}
