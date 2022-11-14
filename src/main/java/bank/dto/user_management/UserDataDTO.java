package bank.dto.user_management;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserDataDTO {
  
  @ApiModelProperty(position = 0)
  private String customerName;
  @ApiModelProperty(position = 1)
  private String userEmail;
  @ApiModelProperty(position = 2)
  private String password;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
