package com.ocado.basket;

record Supplier(String name) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier supplier)) return false;
        return name.equals(supplier.name);
    }

    @Override
    public String toString() {
        return name;
    }
}