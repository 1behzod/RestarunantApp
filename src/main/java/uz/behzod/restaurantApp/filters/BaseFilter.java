package uz.behzod.restaurantApp.filters;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class BaseFilter extends SimpleFilter {

    Long menuId;

    Long employeeId;

    Long companyId;

    Long branchId;

    Long departmentId;

    Long positionId;

    String status;

    String type;

    LocalDateTime fromDate;

    LocalDateTime toDate;

    Long regionId;

    Long districtId;

    Long neighborhoodId;

    //Start day of this day. 00:00
    public LocalDateTime getFromDate() {
        return fromDate.toLocalDate().atStartOfDay();
    }

    //End day of this day 23:59:59
    public LocalDateTime getToDate() {
        return toDate.toLocalDate().atTime(LocalTime.MAX);
    }
}
