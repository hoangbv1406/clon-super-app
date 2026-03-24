package com.project.shopapp.domains.catalog.api;
import java.util.List;
import java.util.Map;

public interface OptionValueInternalApi {
    /**
     * Map một mảng OptionValue IDs thành Key-Value JSON để lưu cứng vào bảng Variant.
     * VD input: [1, 5] -> output: {"Color": "Red", "RAM": "8GB"}
     */
    Map<String, String> generateAttributesMap(List<Integer> optionValueIds);
}