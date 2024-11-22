package uz.behzod.restaurantApp.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class CommonDTO implements Serializable {

    protected Long id;

    protected String name;

    public CommonDTO() {}
}
