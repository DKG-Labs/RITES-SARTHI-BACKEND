package com.sarthi.entity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "USER_MASTER")
@Data
public class UserMaster implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "role_name")
    private String roleName;

    private String employeeId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILENUMBER")
    private String mobileNumber;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (roleName == null || roleName.trim().isEmpty()) {
            return Collections.singletonList(new SimpleGrantedAuthority("USER"));
        }

        // Convert comma-separated roles → authorities
        return Arrays.stream(roleName.split(","))
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .toList();  // This is OK in Java 17+
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
