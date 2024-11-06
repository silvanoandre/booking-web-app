package com.sapm.booking.app.model.dto;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {

    @ExcelCell(0)
    private String name;

    @ExcelCell(1)
    private String description;

    @ExcelCell(2)
    private String unitOfMeasurement;

    @ExcelCell(3)
    private Double price;

}

