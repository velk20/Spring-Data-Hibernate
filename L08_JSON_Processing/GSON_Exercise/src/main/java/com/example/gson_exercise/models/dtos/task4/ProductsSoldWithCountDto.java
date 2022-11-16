package com.example.gson_exercise.models.dtos.task4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsSoldWithCountDto {
    @Expose
    @SerializedName("count")
    private int soldProductsCount;
    @Expose
    @SerializedName("products")
    private List<ProductStatDTO> soldProducts;

    public ProductsSoldWithCountDto(List<ProductStatDTO> products) {
        this.soldProducts = products;
        this.soldProductsCount = products.size();
    }

    public void setSoldProductsCount(int soldProductsCount) {
        this.soldProductsCount = soldProductsCount;
    }

    public void setSoldProducts(List<ProductStatDTO> soldProducts) {
        this.soldProducts = soldProducts;
        this.soldProductsCount = soldProducts.size();
    }
}
