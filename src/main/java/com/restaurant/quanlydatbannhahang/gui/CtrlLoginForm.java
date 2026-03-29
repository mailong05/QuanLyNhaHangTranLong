
package com.restaurant.quanlydatbannhahang.gui;

public class CtrlLoginForm {
    private LoginForm view;
    private AuthService authService; 

    public CtrlLoginForm(LoginForm view, AuthService authService) {
        this.view = view;
        this.authService = authService;
    }
}