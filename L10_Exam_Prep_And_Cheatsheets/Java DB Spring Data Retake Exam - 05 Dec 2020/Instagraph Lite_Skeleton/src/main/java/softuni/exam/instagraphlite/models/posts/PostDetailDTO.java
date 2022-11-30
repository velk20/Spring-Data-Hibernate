package softuni.exam.instagraphlite.models.posts;

public class Post {
    private String caption;
    private double pictureSize;

    public PostDetailDTO(String caption, double pictureSize) {
        this.caption = caption;
        this.pictureSize = pictureSize;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public double getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(double pictureSize) {
        this.pictureSize = pictureSize;
    }
}
