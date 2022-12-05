package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false)
    private Picture profilePicture;

    @OneToMany(mappedBy = "user", targetEntity = Post.class, fetch = FetchType.EAGER)
    private List<Post> posts;

    public User() {
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {


        return String.format("User: %s\n"
        + "Post count: %d\n"
        + "==Post Details:\n"
        + "----Caption: %s\n"
        + "----Picture Size: ",
                this.username,
                this.posts.size(),
                posts.stream().sorted(Comparator.comparingDouble(p -> p.getPicture().getSize())).map(Post::toString).collect(Collectors.joining(System.lineSeparator())));
    }
}
