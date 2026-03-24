package com.project.shopapp.domains.catalog.api;
import java.util.List;

public interface OptionInternalApi {
    /**
     * Xác thực xem tất cả các option_id truyền vào có tồn tại và đang active không.
     */
    boolean areOptionsValidAndActive(List<Integer> optionIds);
}