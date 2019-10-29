package com.example.usermanagementservice.repository;

import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    @Query("select u.name from User u where u.id in (:pIdList)")
    List<String> findByIds(@Param("pIdList") List<Long> idList);

}
