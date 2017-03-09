package com.craftsmen.Models;

import java.io.Serializable;

/**
 * Created by lenovo on 2/24/2017.
 */

public class User_model implements Serializable {
   String U_name , U_city, U_service , U_address , U_password, U_p_num, U_Pic,U_Id, U_type;
    String U_email , U_gender , U_join_date  , location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getU_email() {
        return U_email;
    }

    public void setU_email(String u_email) {
        U_email = u_email;
    }

    public String getU_gender() {
        return U_gender;
    }

    public void setU_gender(String u_gender) {
        U_gender = u_gender;
    }

    public String getU_join_date() {
        return U_join_date;
    }

    public void setU_join_date(String u_join_date) {
        this.U_join_date = u_join_date;
    }

    public String getU_Id() {
        return U_Id;
    }

    public void setU_Id(String u_Id) {
        U_Id = u_Id;
    }

    public String getU_type() {
        return U_type;
    }

    public void setU_type(String u_type) {
        U_type = u_type;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getU_city() {
        return U_city;
    }

    public void setU_city(String u_city) {
        U_city = u_city;
    }

    public String getU_service() {
        return U_service;
    }

    public void setU_service(String u_service) {
        U_service = u_service;
    }

    public String getU_address() {
        return U_address;
    }

    public void setU_address(String u_address) {
        U_address = u_address;
    }

    public String getU_password() {
        return U_password;
    }

    public void setU_password(String u_password) {
        U_password = u_password;
    }

    public String getU_p_num() {
        return U_p_num;
    }

    public void setU_p_num(String u_p_num) {
        U_p_num = u_p_num;
    }

    public String getU_Pic() {
        return U_Pic;
    }

    public void setU_Pic(String u_Pic) {
        U_Pic = u_Pic;
    }
}
