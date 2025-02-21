package uz.behzod.restaurantApp.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUser extends User {

    Long userId;
    Long companyId;
    Long branchId;
    Long departmentId;
    String tin;

    public CustomUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long userId,
            Long companyId,
            Long branchId,
            Long departmentId,
            String tin
    ) {
        super(username, password, authorities);
        this.userId = userId;
        this.companyId = companyId;
        this.branchId = branchId;
        this.departmentId = departmentId;
        this.tin = tin;
    }
}
