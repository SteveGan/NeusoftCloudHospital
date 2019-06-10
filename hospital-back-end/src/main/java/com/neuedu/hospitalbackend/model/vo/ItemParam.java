package com.neuedu.hospitalbackend.model.vo;

public class ItemParam {
    private String itemId; //小项id
    private String itemName; //小项名称
    private Short amount; //数量

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }
}
