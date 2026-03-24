// --- api/FavoriteInternalApi.java ---
package com.project.shopapp.domains.catalog.api;
import java.util.List;

public interface FavoriteInternalApi {
    // Check xem User đã thả tim sản phẩm này chưa (để UI tô đỏ trái tim)
    boolean isProductFavoritedByUser(Integer userId, Integer productId);

    // Trả về danh sách Khách hàng đang mong chờ Sản phẩm này (Phục vụ Marketing)
    List<Integer> getUsersWhoFavoritedProduct(Integer productId);
}