package com.example.androidproject_coupon.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidproject_coupon.models.Product;
import com.example.androidproject_coupon.repositories.ShopRepo;

import java.util.List;

public class ShopViewModel extends ViewModel {

    ShopRepo shopRepo = new ShopRepo();

    MutableLiveData<Product> mutableProduct = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts() {
        return shopRepo.getProducts();
    }

    public  void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public  LiveData<Product> getProduct() {
        return mutableProduct;
    }
}
