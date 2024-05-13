package pattern23.prototype;

import java.io.*;

/**
 * TODO 原型模式-浅克隆
 *
 * @author Mr.He
 * 2020/7/14 15:55
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Flower implements Cloneable, Serializable {
    private String name;
    private Color color;

    public Flower(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public Flower() {
    }

    /**
     * 浅克隆
     */
    @Override
    public Flower clone() throws CloneNotSupportedException {
        return (Flower) super.clone();
    }

    /**
     * 深克隆
     */
    public Flower deepClone() throws CloneNotSupportedException {
        Flower flower = (Flower) super.clone();
        flower.color = this.color.clone();
        return flower;
    }

    /**
     * 序列化方式实现深拷贝
     * CPU密集型操作，会对性能有较大影响，不建议使用
     */
    public Flower serializableClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArray));
        Flower obj = (Flower) objectInputStream.readObject();
        objectOutputStream.close();
        objectInputStream.close();
        return obj;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String toString() {
        return "Flower(name=" + this.getName() + ", color=" + this.getColor() + ")";
    }
}
