package com.panelitapi.repository;

import com.panelitapi.model.Follower;
import com.panelitapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    List<Follower> findFollowersByFollowed(User followed);

    List<Follower> findFollowersByFollower(User follower);

    Follower findFollowerByFollowerAndFollowed(User follower, User followed);
}
