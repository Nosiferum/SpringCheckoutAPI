package com.dogukan.springcheckoutapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    @Entity
    @Table(name = "vas_item")
    public class VasItem {
        @Id
        @Column(name = "id")
        private int id;

        @Column(name = "category_id")
        private int categoryId;

        @Column(name = "seller_id")
        private int sellerId;

        @Column(name = "price")
        private double price;

        @Column(name = "quantity")
        private int quantity;

        public VasItem() {
        }

        public VasItem(int id, int categoryId, int sellerId, double price, int quantity) {
            this.id = id;
            this.categoryId = categoryId;
            this.sellerId = sellerId;
            this.price = price;
            this.quantity = quantity;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getSellerId() {
            return sellerId;
        }

        public void setSellerId(int sellerId) {
            this.sellerId = sellerId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "VasItem{" +
                    "vasItemId=" + id +
                    ", categoryId=" + categoryId +
                    ", sellerId=" + sellerId +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }
    }
