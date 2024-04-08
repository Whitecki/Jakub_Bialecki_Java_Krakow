package com.ocado.basket;

record Product(String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return name.equals(product.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
