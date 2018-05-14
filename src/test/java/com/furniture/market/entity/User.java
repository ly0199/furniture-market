package com.furniture.market.entity;

import lombok.Data;

/**
 * @author Lijq
 */
@Data
public class User{

    private String username;

    private Area area;



    public static void main(String[] args) {

        Area area = new Area();
        area.setName("111");

        User user = new User();
        user.setArea(area);

        System.out.println(user.toString());
    }
}
