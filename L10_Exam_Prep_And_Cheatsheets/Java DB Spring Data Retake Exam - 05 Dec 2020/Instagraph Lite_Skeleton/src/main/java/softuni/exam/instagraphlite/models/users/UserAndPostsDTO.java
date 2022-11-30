package softuni.exam.instagraphlite.models.users;

import softuni.exam.instagraphlite.models.posts.Post;

import java.util.List;

public class UserAndPostsDTO {
    private String username;
    private int postCount;
    private List<Post> details;

    public UserAndPostsDTO(String username, int postCount, List<Post> details) {
        this.username = username;
        this.postCount = postCount;
        this.details = details;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public List<Post> getDetails() {
        return details;
    }

    public void setDetails(List<Post> details) {
        this.details = details;
    }
}
