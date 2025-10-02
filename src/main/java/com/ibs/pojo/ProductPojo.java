package com.ibs.pojo;

import com.ibs.utill.enums.ProductType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {

    private String name;

    private ProductType type;

    private boolean exotic;
}
