package com.panelitapi.repository;

import com.panelitapi.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByNickname(@Size(max = 20) @NotNull(message = "Field nickname can not be null") @Pattern(regexp = "[-A-Za-z0-9ÑñÁÉÍÓÚÇáéíóúçÀÈÌÒÙàèìòùÂÊÎÔÛâêîôûÄËÏÖÜäëïöü_/\\\\.|]{6,25}", message = "Nickname must be at least 6 characters long, include numbers, and common symbols and letters.") String nickname);

    Optional<User> findUserByEmail(@Size(max = 70) @NotNull(message = "Field email can not be null") @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+(([.][a-z]+)*)[.][a-z]{2,}", message = "Use a valid email format") String email);

    Optional<User> findUserByPhoneNumber(@Size(max = 20) String phoneNumber);

    Optional<User> findUserByEmailAndPassword(@Size(max = 70) @NotNull(message = "Field email can not be null") @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]+(([.][a-z]+)*)[.][a-z]{2,}", message = "Use a valid email format") String email, @Size(max = 60) @NotNull(message = "Field password can not be null") String password);
}
