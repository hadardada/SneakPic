package UsersManagement.Services.UserService;


import Entities.User.User;

public class SkinnyUser {
    String name;
    double rating;
    String email;
    boolean isPhotographer;

    public SkinnyUser(String name, double rating, String email, boolean isPhotographer) {
        this.name = name;
        this.rating = rating;
        this.email = email;
        this.isPhotographer = isPhotographer;
    }

    public SkinnyUser(User user) {
        this.name = user.getFirstLetterIsCapitalize();
        this.rating = user.getRating();
        this.email = user.getUsername();
        this.isPhotographer = user.isPhotographer();
    }



}
