package com.nhoserviceboy.carwash.Admin.Model;

public class CarModel {
    String carName,carModel,packageId;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public CarModel(String carName, String carModel, String packageId) {
        this.carName = carName;
        this.carModel = carModel;
        this.packageId = packageId;
    }
}
