package com.restaurant.management.web.request.user;

public final class NewPasswordRequest {
    private String password;
    private String newPassword;
    private String confirmNewPassword;

    public NewPasswordRequest() {
    }

    public NewPasswordRequest(String password, String newPassword, String confirmNewPassword) {
        this.password = password;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
}
