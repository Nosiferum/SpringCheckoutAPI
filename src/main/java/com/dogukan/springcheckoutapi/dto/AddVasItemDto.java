package com.dogukan.springcheckoutapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddVasItemDto extends AddItemDto {
    @NotNull(message = "vas item id cannot be null")
    @Positive(message = "vas item id must be positive")
    private int vasItemId;

    public int getVasItemId() {
        return vasItemId;
    }

    public void setVasItemId(int vasItemId) {
        this.vasItemId = vasItemId;
    }
}