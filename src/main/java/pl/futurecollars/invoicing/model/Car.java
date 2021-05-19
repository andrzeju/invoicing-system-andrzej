package pl.futurecollars.invoicing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Car {

    @JsonIgnore
    @ApiModelProperty(value = "Car id (generated by application)", required = true, example = "1")
    private int id;

    @ApiModelProperty(value = "is the car also for personal user")
    private boolean includingPersonalUse;

    @ApiModelProperty(value = "Registration number", example = "WX 333455")
    private String registration;
}
