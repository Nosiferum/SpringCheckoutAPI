package com.dogukan.springcheckoutapi.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "applied_promotion_id")
    private int appliedPromotionId;

    @Column(name = "total_discount")
    private double totalDiscount;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH,
                    CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "cart_id")
    private List<Item> items = new ArrayList<>();

    public Cart() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getAppliedPromotionId() {
        return appliedPromotionId;
    }

    public void setAppliedPromotionId(int appliedPromotionId) {
        this.appliedPromotionId = appliedPromotionId;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                ", appliedPromotionId=" + appliedPromotionId +
                ", totalDiscount=" + totalDiscount +
                '}';
    }
}
