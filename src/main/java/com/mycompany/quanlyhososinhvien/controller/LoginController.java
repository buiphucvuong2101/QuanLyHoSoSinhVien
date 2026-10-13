package com.mycompany.quanlyhososinhvien.controller;

import com.mycompany.quanlyhososinhvien.database.DBConnection;
import com.mycompany.quanlyhososinhvien.model.User;
import com.mycompany.quanlyhososinhvien.view.LoginFrame;
import com.mycompany.quanlyhososinhvien.view.MainFrame;

import javax.swing.*;
import java.sql.*;

public class LoginController {

    private final LoginFrame view;

    public LoginController(LoginFrame view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.getBtnLogin().addActionListener(e -> xuLyDangNhap());
        view.getBtnExit().addActionListener(e -> System.exit(0));
        view.getTxtPassword().addActionListener(e -> xuLyDangNhap());
        
        view.getBtnRegister().addActionListener(e -> moDangKy());
        view.getBtnForgot().addActionListener(e -> moQuenMatKhau());
    }

    private void xuLyDangNhap() {
        String username = view.getTxtUsername().getText().trim();
        String password = new String(view.getTxtPassword().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ!");
            return;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("id"), rs.getString("username"),
                    rs.getString("password"), rs.getString("hoTen"),
                    rs.getString("role")
                );
                JOptionPane.showMessageDialog(view, "Đăng nhập thành công!");
                view.dispose();
                new MainFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(view, "Sai tên đăng nhập hoặc mật khẩu!");
                view.getTxtPassword().setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage());
        }
    }
    
    // Mở dialog Đăng ký
    private void moDangKy() {
        com.mycompany.quanlyhososinhvien.view.RegisterDialog dlg =
            new com.mycompany.quanlyhososinhvien.view.RegisterDialog(view, true);
        dlg.setLocationRelativeTo(view);
        dlg.setVisible(true);
    }

    // Mở dialog Quên mật khẩu
    private void moQuenMatKhau() {
        com.mycompany.quanlyhososinhvien.view.ForgotDialog dlg =
            new com.mycompany.quanlyhososinhvien.view.ForgotDialog(view, true);
        dlg.setLocationRelativeTo(view);
        dlg.setVisible(true);
    }
}