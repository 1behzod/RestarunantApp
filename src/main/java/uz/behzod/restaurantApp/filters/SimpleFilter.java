package uz.behzod.restaurantApp.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class SimpleFilter {

    Integer page = 0;

    Integer size = 20;

    String search = "";

    String orderBy = "id";

    String sortOrder = "desc";

    public String getSearch() {
        return this.search = this.search != null ? this.search.trim().replaceAll("[.!?,'{}]", "").toLowerCase() : null;
    }

    @JsonIgnore
    public boolean isSearchNotEmpty() {
        return StringUtils.hasLength(search);
    }

    @JsonIgnore
    public String getSearchFor() {
        return "%" + search.toLowerCase() + "%";
    }

    @JsonIgnore
    public String getLikeSearch() {
        return " like (:searchKey)";
    }

    @JsonIgnore
    public Sort getOrderedSortBy() {
        return sortOrder.equals("asc") ? Sort.by(orderBy).ascending() : Sort.by(orderBy).descending();
    }

    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }

    @JsonIgnore
    public Pageable getOrderedPageable() {
        return PageRequest.of(page, size, getOrderedSortBy());
    }

    @JsonIgnore
    public int getStart() {
        return this.getPage() * this.getSize();
    }
}
