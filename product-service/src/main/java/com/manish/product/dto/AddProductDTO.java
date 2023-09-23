package com.manish.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductDTO {
    @Size(min = 1, max = 50)
    @NotBlank
    private String name;
    @Size(min = 2, max = 100)
    private String description;
    @Min(value = 0)
    @NotNull
    private double price;
    @NotBlank
    private String vendorId;
}
