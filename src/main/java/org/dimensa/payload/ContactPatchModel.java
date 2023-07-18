package org.dimensa.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
@Validated
public class ContactPatchModel implements Serializable {
    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("key")
    private String key;

    @ApiModelProperty(required = true, value = "")
    @NotNull
    @JsonProperty("value")
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
