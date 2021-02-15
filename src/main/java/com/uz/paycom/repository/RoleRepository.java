package com.uz.paycom.repository;

import com.uz.paycom.entity.Role;
import com.uz.paycom.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findAllByNameIn(List<RoleName> names);
    Role findByName(RoleName name);


}
