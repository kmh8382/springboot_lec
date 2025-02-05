package com.min.app07.common;

public enum UserRole {
  
  ADMIN("ADMIN"), USER("USER");
  
  private String userRole;
  
  UserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
  
  @Override
  public String toString() {    
    return "UserRole[userRole=" + userRole + "]";
  }
  
}
