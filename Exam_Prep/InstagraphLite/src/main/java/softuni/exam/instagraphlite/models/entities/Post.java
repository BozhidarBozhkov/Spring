package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{

    @Column(nullable = false)
    private String caption;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Picture picture;

    public Post() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("==Post Details:\n"
        + "----Caption: %s\n"
        + "----Picture Size: %.2f", this.caption, this.picture.getSize());
    }
}
