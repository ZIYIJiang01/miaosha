package com.dataobject;

import java.math.BigDecimal;

public class ItemDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.id
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.title
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.price
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private BigDecimal price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.stock
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private Integer stock;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.description
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.sales
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private Integer sales;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_item.img_url
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    private String imgUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.id
     *
     * @return the value of t_item.id
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.id
     *
     * @param id the value for t_item.id
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.title
     *
     * @return the value of t_item.title
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.title
     *
     * @param title the value for t_item.title
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.price
     *
     * @return the value of t_item.price
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.price
     *
     * @param price the value for t_item.price
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.stock
     *
     * @return the value of t_item.stock
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.stock
     *
     * @param stock the value for t_item.stock
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.description
     *
     * @return the value of t_item.description
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.description
     *
     * @param description the value for t_item.description
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.sales
     *
     * @return the value of t_item.sales
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public Integer getSales() {
        return sales;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.sales
     *
     * @param sales the value for t_item.sales
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setSales(Integer sales) {
        this.sales = sales;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_item.img_url
     *
     * @return the value of t_item.img_url
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_item.img_url
     *
     * @param imgUrl the value for t_item.img_url
     *
     * @mbg.generated Sat Jul 20 17:52:48 IST 2024
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
}