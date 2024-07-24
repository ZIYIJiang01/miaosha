package com.service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ItemModel implements java.io.Serializable {
    private Integer id;
    @NotBlank(message ="item title can't be empty")
    private String title;
    @NotNull(message= "item price can't be empty")
    @Min(value=0, message="item price must bigger than 0")
    private BigDecimal price;
    @NotNull(message="stock can't be empty")
    private Integer stock;
    @NotBlank(message="item description can't be empty")
    private String description;
//  aggregate model, if promoModel != null, represents it has promotion still not over(not happen and ongoing
    private PromoModel promoModel;

    public PromoModel getPromoModel() {
        return promoModel;
    }

    public void setPromoModel(PromoModel promoModel) {
        this.promoModel = promoModel;
    }

    private Integer sales;
//    item description img
    @NotBlank(message="item picture can't be empty")
    private String imgUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
