package uz.behzod.restaurantApp.dto.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenDTO {

    String scope;

    Integer expire;

    String access_token;

    String token_type;

    String refresh_token;

}
