package io.github.szokecsaba.vgdb.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public class PagingUtil {
    private Integer page;
    private Integer pageSize;
    private Integer currentPage;

    @Value("${sort.by.default}")
    private String sortBy;

    @Value("${sort.direction.default}")
    private Sort.Direction sortDirection;

    public void setPage(Integer page) {
        this.page = page != null ? page : 0;

        setCurrentPage();
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setSort(String sort) {
        if (sort != null) {
            sortBy = sort.split("-")[0];
            String sortDirectionTemp = sort.split("-")[1];
            sortDirection = sortDirectionTemp.equalsIgnoreCase("asc")
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
    }

    private void setCurrentPage() {
        this.currentPage = Math.max(0, page - 1);
    }

    public Pageable getPageable() {
        if (sortBy != null && sortDirection != null) {
            return PageRequest.of(currentPage, pageSize, Sort.by(sortDirection, sortBy));
        }

        return PageRequest.of(currentPage, pageSize);
    }

    public Map<String, Object> getResponse(Page<?> items, String itemsName) {

        Map<String, Long> paging = Map.of(
                "pages", (long) items.getTotalPages(),
                "currentPage", (long) currentPage + 1,
                "numOfItems", (long) items.getNumberOfElements(),
                "totalNumOfItems", items.getTotalElements(),
                "perPage", (long) items.getSize()
        );

        return Map.of(
                itemsName, items.toList(),
                "paging", paging
        );
    }
}
