package org.dimensa.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ApiModel
@Validated
public class AddressModel {
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("street")
    private String street;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("number")
    private Integer number;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("cep")
    private String cep;

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCep() {
        return cep;
    }
}
