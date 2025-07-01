import com.formdev.flatlaf.FlatDarkLaf;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

public class StudentGUI extends JFrame {
    JTextField nameField, ageField, courseField;
    JTable studentTable;

    JTextField searchIdField, searchNameField, searchAgeField, searchCourseField;
    JTextField updateIdField, updateNameField, updateAgeField, updateCourseField;
    JTextField deleteIdField;
    JTextField filterField;

    public StudentGUI() {
        setTitle("\uD83C\uDF93 Student Management System");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabs.setBounds(0, 60, 850, 500);

        // Add Student Tab
        JPanel addPanel = createFormPanel();
        nameField = new JTextField(); ageField = new JTextField(); courseField = new JTextField();
        JButton addBtn = createButton("Add Student", new Color(76, 175, 80));
        addBtn.addActionListener(e -> addStudent());

        addPanel.add(new JLabel("\uD83D\uDC64 Name:"));   addPanel.add(nameField);
        addPanel.add(new JLabel("\uD83C\uDF82 Age:"));    addPanel.add(ageField);
        addPanel.add(new JLabel("\uD83D\uDCDA Course:")); addPanel.add(courseField);
        addPanel.add(new JLabel());                         addPanel.add(addBtn);
        tabs.add("➕ Add", addPanel);

        // View Students Tab
        JPanel viewPanel = new JPanel(new BorderLayout(10, 10));
        studentTable = new JTable();
        JScrollPane scroll = new JScrollPane(studentTable);

        JPanel topViewPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterField = new JTextField(15);
        JButton filterBtn = createButton("\uD83D\uDD0D Filter by Course", new Color(100, 181, 246));
        filterBtn.addActionListener(e -> filterByCourse());
        JButton refreshBtn = createButton("\uD83D\uDD04 Refresh", new Color(33, 150, 243));
        refreshBtn.addActionListener(e -> loadStudents());
        JButton exportBtn = createButton("\uD83D\uDCC4 Export to CSV", new Color(255, 112, 67));
        exportBtn.addActionListener(e -> exportToCSV());
        topViewPanel.add(new JLabel("Filter Course:"));
        topViewPanel.add(filterField);
        topViewPanel.add(filterBtn);
        topViewPanel.add(refreshBtn);
        topViewPanel.add(exportBtn);

        viewPanel.add(topViewPanel, BorderLayout.NORTH);
        viewPanel.add(scroll, BorderLayout.CENTER);
        tabs.add("\uD83D\uDCCB View", viewPanel);

        // Search Student Tab
        JPanel searchPanel = createFormPanel();
        searchIdField = new JTextField();
        searchNameField = new JTextField(); searchNameField.setEditable(false);
        searchAgeField = new JTextField(); searchAgeField.setEditable(false);
        searchCourseField = new JTextField(); searchCourseField.setEditable(false);
        JButton searchBtn = createButton("\uD83D\uDD0D Search", new Color(255, 193, 7));
        searchBtn.addActionListener(e -> searchStudent());

        searchPanel.add(new JLabel("\uD83D\uDD0E Student ID:")); searchPanel.add(searchIdField);
        searchPanel.add(new JLabel("\uD83D\uDC64 Name:"));       searchPanel.add(searchNameField);
        searchPanel.add(new JLabel("\uD83C\uDF82 Age:"));        searchPanel.add(searchAgeField);
        searchPanel.add(new JLabel("\uD83D\uDCDA Course:"));     searchPanel.add(searchCourseField);
        searchPanel.add(new JLabel());                             searchPanel.add(searchBtn);
        tabs.add("\uD83D\uDD0D Search", searchPanel);

        // Update Student Tab
        JPanel updatePanel = createFormPanel();
        updateIdField = new JTextField(); updateNameField = new JTextField();
        updateAgeField = new JTextField(); updateCourseField = new JTextField();
        JButton updateBtn = createButton("\u270F\uFE0F Update", new Color(0, 150, 136));
        updateBtn.addActionListener(e -> updateStudent());

        updatePanel.add(new JLabel("\uD83C\uDD94 ID:"));              updatePanel.add(updateIdField);
        updatePanel.add(new JLabel("\uD83D\uDC64 New Name:"));       updatePanel.add(updateNameField);
        updatePanel.add(new JLabel("\uD83C\uDF82 New Age:"));        updatePanel.add(updateAgeField);
        updatePanel.add(new JLabel("\uD83D\uDCDA New Course:"));    updatePanel.add(updateCourseField);
        updatePanel.add(new JLabel());                                 updatePanel.add(updateBtn);
        tabs.add("\u270F\uFE0F Update", updatePanel);

        // Delete Student Tab
        JPanel deletePanel = createFormPanel();
        deleteIdField = new JTextField();
        JButton deleteBtn = createButton("\uD83D\uDDD1\uFE0F Delete", new Color(244, 67, 54));
        deleteBtn.addActionListener(e -> deleteStudent());

        deletePanel.add(new JLabel("Enter ID:")); deletePanel.add(deleteIdField);
        deletePanel.add(new JLabel());            deletePanel.add(deleteBtn);
        tabs.add("\uD83D\uDDD1\uFE0F Delete", deletePanel);

        // Chart Tab
        JPanel chartPanel = new JPanel(new BorderLayout());
        JButton loadChart = createButton("\uD83D\uDCC8 Load Chart", new Color(103, 58, 183));
        chartPanel.add(loadChart, BorderLayout.NORTH);
        tabs.add("\uD83D\uDCCA Course Chart", chartPanel);

        loadChart.addActionListener(e -> {
            try {
                Connection conn = DBConnect.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT course, COUNT(*) AS count FROM students GROUP BY course");

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                while (rs.next()) {
                    dataset.addValue(rs.getInt("count"), "Students", rs.getString("course"));
                }
                JFreeChart chart = ChartFactory.createBarChart("Students Per Course", "Course", "Total Students", dataset);
                ChartPanel panel = new ChartPanel(chart);
                chartPanel.removeAll();
                chartPanel.add(loadChart, BorderLayout.NORTH);
                chartPanel.add(panel, BorderLayout.CENTER);
                chartPanel.validate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(tabs);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(63, 81, 181));
        welcomePanel.setBounds(0, -60, 850, 60);
        welcomePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel welcomeLabel = new JLabel("\uD83C\uDF89 Welcome, Mayur!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        getLayeredPane().add(welcomePanel, JLayeredPane.POPUP_LAYER);

        new Timer(5, new ActionListener() {
            int y = -60;
            public void actionPerformed(ActionEvent e) {
                if (y < 0) {
                    y++;
                    welcomePanel.setLocation(0, y);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();

        loadStudents();
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 15, 10));
        panel.setBorder(new EmptyBorder(20, 50, 20, 50));
        panel.setBackground(Color.white);
        return panel;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(bg.darker());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(bg);
            }
        });

        return btn;
    }

    private void addStudent() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String course = courseField.getText();

            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO students(name, age, course) VALUES (?, ?, ?)");
            ps.setString(1, name); ps.setInt(2, age); ps.setString(3, course);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Student added!");
                nameField.setText(""); ageField.setText(""); courseField.setText("");
                loadStudents();
            }

            ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudents() {
        try {
            Connection conn = DBConnect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Name", "Age", "Course"});

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course")
                });
            }

            studentTable.setModel(model);
            rs.close(); stmt.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterByCourse() {
        try {
            String course = filterField.getText();
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE course LIKE ?");
            ps.setString(1, "%" + course + "%");
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{"ID", "Name", "Age", "Course"});

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course")
                });
            }

            studentTable.setModel(model);
            rs.close(); ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportToCSV() {
        try {
            FileWriter fw = new FileWriter("students.csv");
            PrintWriter pw = new PrintWriter(fw);
            DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                pw.print(model.getColumnName(i) + ",");
            }
            pw.println();

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    pw.print(model.getValueAt(i, j) + ",");
                }
                pw.println();
            }
            pw.close();
            JOptionPane.showMessageDialog(this, "\uD83D\uDCC4 Exported to students.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchStudent() {
        try {
            int id = Integer.parseInt(searchIdField.getText());
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                searchNameField.setText(rs.getString("name"));
                searchAgeField.setText(String.valueOf(rs.getInt("age")));
                searchCourseField.setText(rs.getString("course"));
            } else {
                JOptionPane.showMessageDialog(this, "❌ Student not found!");
                searchNameField.setText(""); searchAgeField.setText(""); searchCourseField.setText("");
            }

            rs.close(); ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStudent() {
        try {
            int id = Integer.parseInt(updateIdField.getText());
            String name = updateNameField.getText();
            int age = Integer.parseInt(updateAgeField.getText());
            String course = updateCourseField.getText();

            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE students SET name=?, age=?, course=? WHERE id=?");
            ps.setString(1, name); ps.setInt(2, age); ps.setString(3, course); ps.setInt(4, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Student updated!");
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "❌ ID not found.");
            }

            ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent() {
        try {
            int id = Integer.parseInt(deleteIdField.getText());
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE id = ?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✅ Student deleted!");
                loadStudents();
            } else {
                JOptionPane.showMessageDialog(this, "❌ ID not found.");
            }

            ps.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        SwingUtilities.invokeLater(() -> new StudentGUI());
    }
}
