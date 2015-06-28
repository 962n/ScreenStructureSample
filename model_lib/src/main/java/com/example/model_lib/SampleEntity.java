package com.example.model_lib;

import java.io.Serializable;

/**
 * サンプルEntityクラス
 */
public class SampleEntity implements Serializable {
    private int no;
    private String name;
    private String age;
    private String height;
    private String weight;

    public void setNo(int no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        StringBuilder builder =  new StringBuilder();
        builder.append("no =").append(this.no).append("¥n");
        builder.append("name =").append(this.name).append("¥n");
        builder.append("age =").append(this.age).append("¥n");
        builder.append("height =").append(this.height).append("¥n");
        builder.append("weight =").append(this.weight).append("¥n");
        return builder.toString();
    }
}
