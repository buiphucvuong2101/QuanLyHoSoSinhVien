package com.mycompany.quanlyhososinhvien.view;

import com.mycompany.quanlyhososinhvien.model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private SinhVienPanel sinhVienPanel;
    private KhoaPanel khoaPanel;
    private LopPanel lopPanel;
    private SearchPanel searchPanel;
    private StatisticsPanel statsPanel;

    public MainFrame(User user) {
        setTitle("Quản lý hồ sơ sinh viên - " + user.getHoTen() 
                + " (" + user.getRole() + ")");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo các Panel
        sinhVienPanel = new SinhVienPanel(user);
        khoaPanel = new KhoaPanel(user);
        lopPanel = new LopPanel(user);
        searchPanel = new SearchPanel();
        statsPanel = new StatisticsPanel();

        // Tạo TabbedPane
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Sinh viên", sinhVienPanel);
        tabs.addTab("Khoa", khoaPanel);
        tabs.addTab("Lớp", lopPanel);
        tabs.addTab("Tìm kiếm", searchPanel);
        tabs.addTab("Thống kê", statsPanel);

        // ⭐ Tự động reload khi chuyển tab
        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            switch (idx) {
                case 0: if (sinhVienPanel != null) sinhVienPanel.reloadData(); break;
                case 1: if (khoaPanel != null) khoaPanel.reloadData(); break;
                case 2: if (lopPanel != null) lopPanel.reloadData(); break;
                case 3: if (searchPanel != null) searchPanel.reloadData(); break;
                case 4: if (statsPanel != null) statsPanel.reloadData(); break;
            }
        });

        add(tabs, BorderLayout.CENTER);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Hệ thống");
        JMenuItem mniLogout = new JMenuItem("Đăng xuất");
        JMenuItem mniExit = new JMenuItem("Thoát");

        mniLogout.addActionListener(e -> {
            int cf = JOptionPane.showConfirmDialog(this,
                "Đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (cf == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        mniExit.addActionListener(e -> System.exit(0));

        fileMenu.add(mniLogout);
        fileMenu.add(mniExit);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}