package com.korolchuk1986.mySocialNetwork.repo;

import com.korolchuk1986.mySocialNetwork.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
