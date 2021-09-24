package com.example.aska;

public class UserLikes {

    public String likesGot;
    public String likedByWhom;

    public UserLikes() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserLikes(String LikesGot, String LikedBy) {
        this.likesGot=LikesGot;
        this.likedByWhom=LikedBy;
    }


    public String getLikesGot() {
        return likesGot;
    }

    public void setLikesGot(String likesGot) {
        this.likesGot = likesGot;
    }

    public String getLikedByWhom() {
        return likedByWhom;
    }

    public void setLikedByWhom(String likedByWhom) {
        this.likedByWhom = likedByWhom;
    }
}
