package net.xdclass.xdclassredis.model;

import java.util.List;

public class VideoCardDO {

    private int id;

    private String title;

    private String img;

    private int weight;

    List<VideoDO> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<VideoDO> getList() {
        return list;
    }

    public void setList(List<VideoDO> list) {
        this.list = list;
    }
}
