package uz.behzod.restaurantApp.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.behzod.restaurantApp.domain.menu.MenuItem;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MenuItemSalesSummary {

    private MenuItem menuItem;  // The menu item that was sold
    private BigDecimal totalQty;  // The total quantity sold for this item
    private BigDecimal totalAmount;  // The total amount sold for this item (sum of total prices)

    public BigDecimal getTotalQty() {
        return totalQty != null ? totalQty : BigDecimal.ZERO;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount != null ? totalAmount : BigDecimal.ZERO;
    }
}
