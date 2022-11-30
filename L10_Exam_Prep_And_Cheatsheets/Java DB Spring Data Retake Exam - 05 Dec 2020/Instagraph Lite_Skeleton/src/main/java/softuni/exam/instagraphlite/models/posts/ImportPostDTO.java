package softuni.exam.instagraphlite.models.posts;

import softuni.exam.instagraphlite.models.pictures.PicturePathDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportPostDTO {
    @XmlElement(name = "caption")
    @NotNull
    @Size(min = 21)
    private String caption;
    @XmlElement(name = "user")
    @NotNull
    private UserUsernameDTO user;
    @NotNull
    @XmlElement(name = "picture")
    private PicturePathDTO picture;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public UserUsernameDTO getUser() {
        return user;
    }

    public void setUser(UserUsernameDTO user) {
        this.user = user;
    }

    public PicturePathDTO getPicture() {
        return picture;
    }

    public void setPicture(PicturePathDTO picture) {
        this.picture = picture;
    }
}
