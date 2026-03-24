package com.project.shopapp.domains.location.api;

public interface LogisticsMappingFacade {
    /**
     * @param internalDistrictCode Mã huyện của hệ thống mình (VD: "001")
     * @param provider "GHTK" hoặc "GHN"
     * @return Mã huyện của bên vận chuyển (VD: "1234")
     */
    String getProviderDistrictId(String internalDistrictCode, String provider);
}