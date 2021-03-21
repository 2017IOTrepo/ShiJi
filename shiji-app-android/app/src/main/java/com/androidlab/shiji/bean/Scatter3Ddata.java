package com.androidlab.shiji.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scatter3Ddata implements Serializable {

    private String name;

    private List<Object> value;

    /**

     */
    public Scatter3Ddata(String name, Object jingdu, Object weidu, Object gaoduz) {
        this.name = name;
        this.value(jingdu, weidu, gaoduz);
    }


    public String name() {
        if (this.name == null) {
            this.name = "";
        }
        return this.name;
    }
    private Scatter3Ddata name(String nam) {
        if (nam == null || nam.equals("")) {
            return this;
        }
        this.name = nam;
        return this;
    }

    /**
     * 获取value值
     */
    public List<Object> value() {
        if (this.value == null) {
            this.value = new ArrayList<Object>();
        }
        return this.value;
    }

    /**
     * 设置values值
     *
     * @param values
     */
    private Scatter3Ddata value(Object... values) {
        if (values == null || values.length == 0) {
            return this;
        }
        this.value().addAll(Arrays.asList(values));
        return this;
    }

}
