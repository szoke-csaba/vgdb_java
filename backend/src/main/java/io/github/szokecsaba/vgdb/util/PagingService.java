package io.github.szokecsaba.vgdb.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PagingService {
    private Integer page;
    private Integer pageSize;
    private Integer currentPage;

    public void setPage(Integer page) {
        this.page = page != null ? page : 0;

        setCurrentPage();
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    private void setCurrentPage() {
        this.currentPage = Math.max(0, page - 1);
    }

    public Pageable getPageable() {
        return PageRequest.of(currentPage, pageSize);
    }

    public Map<String, Object> getResponse(Page<?> items, String itemsName) {
        Map<String, Integer> paging = Map.of(
                "pages", items.getTotalPages(),
                "currentPage", currentPage + 1
        );

        return Map.of(
                itemsName, items.toList(),
                "paging", paging
        );
    }
}
