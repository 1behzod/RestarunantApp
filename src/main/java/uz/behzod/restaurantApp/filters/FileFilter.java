package uz.behzod.restaurantApp.filters;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class FileFilter extends BaseFilter {

    String contentType;

}
