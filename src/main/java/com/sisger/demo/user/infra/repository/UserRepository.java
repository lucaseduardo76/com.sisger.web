package com.sisger.demo.user.infra.repository;

import com.sisger.demo.company.domain.Company;
import com.sisger.demo.user.domain.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    User findByCpf(String cpf);

    List<User> findAllByCompany(Company company);


}
