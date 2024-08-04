package prototype;

import java.io.Serializable;

/**
 * TODO 原型模式-浅克隆
 *
 * @author Mr.He
 * 2020/7/14 15:55
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Color implements Cloneable, Serializable {
    private String color;

    public Color(String color) {
        this.color = color;
    }

    public Color() {
    }

    @Override
    protected Color clone() throws CloneNotSupportedException {
        return (Color) super.clone();
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString() {
        return "Color(color=" + this.getColor() + ")";
    }
}
