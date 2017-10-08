package cn.wangjy.catcher.bean;

/**
 * TODO ADD DESCRIPTION
 *
 * @author wangjieyao
 * @Date 2017/9/26 19:31
 */
public class AmazonComment {

    private String id;
    private String comment;
    private String author;
    private String starts;
    private String date;
    private String color;
    private String size;
    private String verified;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

<<<<<<< HEAD
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AmazonComment{" +
                "comment='" + comment + '\'' +
                ", author='" + author + '\'' +
                ", starts='" + starts + '\'' +
                ", date='" + date + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", verified='" + verified + '\'' +
                '}';
    }


=======
>>>>>>> c6927df8cd7f016b530eb5f65601f35725760f4a
    public void setSize(String size) {
        this.size = size;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
