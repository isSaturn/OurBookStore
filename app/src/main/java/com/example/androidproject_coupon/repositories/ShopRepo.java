package com.example.androidproject_coupon.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidproject_coupon.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID().toString(), "Đắc Nhân Tâm", 80000,"https://salt.tikicdn.com/cache/400x400/ts/product/df/7d/da/d340edda2b0eacb7ddc47537cddb5e08.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Tam Quốc Chí", 140000,"https://salt.tikicdn.com/cache/400x400/ts/product/0a/5f/98/b6aa82257839892431c5c029bb2b7404.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Trí Tuệ Do Thái", 120000,"https://salt.tikicdn.com/cache/400x400/ts/product/fb/1d/34/4a5be4b48fbb204120983eae008c2705.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Tư Duy LOGIC", 80000, "https://salt.tikicdn.com/cache/400x400/ts/product/41/0a/e2/92d8be8c47a54f951fa50769e8ea0ac2.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Gương Kiên Nhẫn", 90000, "https://salt.tikicdn.com/cache/400x400/ts/product/42/b6/43/576a9ebd3442a4155d19eab398b5bfcd.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Sherlock Holmes", 90000, "https://salt.tikicdn.com/cache/400x400/media/catalog/product/i/m/img930_2.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Cảm Xúc", 80000, "https://salt.tikicdn.com/cache/400x400/ts/product/01/18/96/97faff7fa3dc428f173d21d720edb8d9.jpg.webp"));
        productList.add(new Product(UUID.randomUUID().toString(), "Bí Ẩn", 50000, "https://salt.tikicdn.com/cache/400x400/ts/product/62/d6/cb/17d93a4c8baf95c6a0deb23d463093a3.jpg.webp"));
        mutableProductList.setValue(productList);
    }
}
