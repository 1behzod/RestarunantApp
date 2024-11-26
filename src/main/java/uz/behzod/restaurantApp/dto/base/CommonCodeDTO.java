package uz.behzod.restaurantApp.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonCodeDTO extends CommonDTO {

    String code;

    public CommonCodeDTO(Long id, String name, String code) {
        super(id, name);
        this.code = code;
    }
}
