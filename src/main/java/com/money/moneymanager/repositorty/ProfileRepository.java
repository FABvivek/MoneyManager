package com.money.moneymanager.repositorty;

import com.money.moneymanager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity , Long> {


    //Select * from tbl_profile where email = ?
    Optional<ProfileEntity> findByEmail(String email);
}
