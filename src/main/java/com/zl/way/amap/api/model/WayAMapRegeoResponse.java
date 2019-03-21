package com.zl.way.amap.api.model;

public class WayAMapRegeoResponse {

    private String cityCode;
    private String cityName;
    private String adCode;
    private String formatAddress;
    private String district;

    private String neighborhoodName;
    private String neighborhoodLocation;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getFormatAddress() {
        return formatAddress;
    }

    public void setFormatAddress(String formatAddress) {
        this.formatAddress = formatAddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public String getNeighborhoodLocation() {
        return neighborhoodLocation;
    }

    public void setNeighborhoodLocation(String neighborhoodLocation) {
        this.neighborhoodLocation = neighborhoodLocation;
    }
}
