package com.leilei.entity;

/**
 * @author lei
 * @desc
 * @version 1.0
 * @date 2021-02-25 17:37
 */

/**
 * vehicle_alarm
 */
public class VehicleAlarm {
    /**
     * id
     */
    private Integer id;

    /**
     * licensePlate
     */
    private String licensePlate;

    /**
     * plateColor
     */
    private String plateColor;

    /**
     * deviceTime
     */
    private Long deviceTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public Long getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(Long deviceTime) {
        this.deviceTime = deviceTime;
    }
}