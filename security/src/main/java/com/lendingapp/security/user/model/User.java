package com.lendingapp.security.user.model;

import javax.persistence.*;

@Entity
public class User {
  @Id
  @GeneratedValue
  private long id;

  @JoinColumn
  @OneToOne(cascade = CascadeType.ALL)
  private UserDetailsImpl userDetails;

  public User() {
  }

  public User(UserDetailsImpl userDetails) {
    this.userDetails = userDetails;
  }

  public long getId() {
    return id;
  }

  public UserDetailsImpl getUserDetails() {
    return userDetails;
  }

  public void setUserDetails(UserDetailsImpl userDetails) {
    this.userDetails = userDetails;
  }
}
