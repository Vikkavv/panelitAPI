package com.panelitapi.service;

import com.panelitapi.model.Follower;
import com.panelitapi.model.FollowerId;
import com.panelitapi.model.User;
import com.panelitapi.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowerService {

    UserService userService;
    FollowerRepository followerRepository;

    @Autowired
    public FollowerService(UserService userService, FollowerRepository followerRepository) {
        this.userService = userService;
        this.followerRepository = followerRepository;
    }

    public boolean addFollow(User followerUser, User followed) {
        FollowerId followerId = new FollowerId();
        Follower follower = new Follower();

        follower.setId(followerId);
        follower.setFollower(userService.findById(followerUser.getId()));
        follower.setFollowed(userService.findById(followed.getId()));
        followerRepository.save(follower);
        return true;
    }

    public boolean removeFollow(User followerUser, User followed) {
        followerUser = userService.findById(followerUser.getId());
        followed = userService.findById(followed.getId());
        Follower follower = followerRepository.findFollowerByFollowerAndFollowed(followerUser, followed);
        followerRepository.delete(follower);
        return true;
    }

    public List<User> findFollowersOfUser(long userId) {
        User user = userService.findById(userId);
        List<User> userFollowers = new ArrayList<>();

        List<Follower> followers = followerRepository.findFollowersByFollowed(user);
        for (Follower follower : followers) {
            userFollowers.add(follower.getFollower());
        }
        return userFollowers;
    }

    public List<User> findFollowedOfUser(long userId) {
        User user = userService.findById(userId);
        List<User> userFollowed = new ArrayList<>();

        List<Follower> followed = followerRepository.findFollowersByFollower(user);
        for (Follower follower : followed) {
            userFollowed.add(follower.getFollowed());
        }
        return userFollowed;
    }
}
