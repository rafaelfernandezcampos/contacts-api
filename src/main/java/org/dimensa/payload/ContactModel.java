package org.dimensa.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel
@Validated
public class ContactModel implements Serializable {
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("email")
    private String email;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("birth")
    private LocalDateTime birth;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("addresses")
    private List<AddressModel> addresses;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<AddressModel> getAddresses() {
        return addresses;
    }

    public LocalDateTime getBirth() {
        return birth;
    }
}
