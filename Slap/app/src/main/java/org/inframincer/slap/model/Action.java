package org.inframincer.slap.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by yoon on 2017. 10. 11..
 */

@IgnoreExtraProperties
public class Action {

    public double maxValue;
    public double minValue;
    public String action1;
    public String action2;
    public String action3;
    public int stage;
    public String name;
    public String scope;
    public String message;

    public Action() {
    }

    public Action(double maxValue, double minValue,
                  String action1, String action2, String action3,
                  int stage, String name, String scope, String message) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.action1 = action1;
        this.action2 = action2;
        this.action3 = action3;
        this.stage = stage;
        this.name = name;
        this.scope = scope;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Action{" +
                "maxValue=" + maxValue +
                ", minValue=" + minValue +
                ", action1='" + action1 + '\'' +
                ", action2='" + action2 + '\'' +
                ", action3='" + action3 + '\'' +
                ", stage=" + stage +
                ", name='" + name + '\'' +
                ", scope='" + scope + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
