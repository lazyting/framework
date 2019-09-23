package model;

import annotation.HideInfo;
import utils.StringUtils;

import javax.persistence.Column;

public class TestModel {
    private String name;
    @HideInfo(type = "phone")
    private String phone;
    @HideInfo(type = "idcardNum")
    @Column()
    private String idcardNum;

    public String getvIdCardNum() {
        return StringUtils.hideIdCardNum(idcardNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdcardNum() {
        return StringUtils.hideIdCardNum(idcardNum);
    }

    public void setIdcardNum(String idcardNum) {
        this.idcardNum = idcardNum;
    }

    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idcardNum='" + idcardNum + '\'' +
                '}';
    }
}
