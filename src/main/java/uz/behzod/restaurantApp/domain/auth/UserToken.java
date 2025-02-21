package uz.behzod.restaurantApp.domain.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.constants.Constants;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.domain.Position;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.enums.UserStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "token")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE token SET deleted = 'true' WHERE id=?")
public class UserToken extends SimpleEntity {

    @Column(name = "scope")
    String scope;

    @Column(name = "expire_date")
    LocalDateTime expireDate;

    @Column(name = "access_token")
    String accessToken;

    @Column(name = "token_type")
    String tokenType;

    @Column(name = "refresh_token")
    String RefreshToken;

}

