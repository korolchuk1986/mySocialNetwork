package com.korolchuk1986.mySocialNetwork.repo;

import com.korolchuk1986.mySocialNetwork.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
