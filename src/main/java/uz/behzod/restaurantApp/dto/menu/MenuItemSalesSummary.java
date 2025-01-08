package uz.behzod.restaurantApp.dto.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemSalesSummary {

    Long menuItemId;  // The menu item that was sold
    Long totalOrders;  // The total quantity sold for this item (use Long instead of BigDecimal)
    BigDecimal totalQtySold;  // The total quantity sold for this item
    BigDecimal totalSales;  // The total amount sold for this item (sum of total prices)

    public MenuItemSalesSummary(Long menuItemId, Long totalOrders, BigDecimal totalQtySold, BigDecimal totalSales) {
        this.menuItemId = menuItemId;
        this.totalOrders = totalOrders;
        this.totalQtySold = totalQtySold;
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalQtySold() {
        return totalQtySold != null ? totalQtySold : BigDecimal.ZERO;
    }

    public BigDecimal getTotalSales() {
        return totalSales != null ? totalSales : BigDecimal.ZERO;
    }
}
