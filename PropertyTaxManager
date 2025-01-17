package QuanLyThueNhaDat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class PropertyTaxManager extends JFrame implements ActionListener {
    private JTextField taxIdField, ownerNameField, areaField, searchField;
    private JButton addButton, editButton, deleteButton, searchButton;
    private ArrayList<PropertyTax> propertyList;
    private PropertyTaxTableModel tableModel;
    private JTable propertyTable;
    private Connection connection;

    public PropertyTaxManager() {
        setTitle("Quản lý thuế nhà đất");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        propertyList = new ArrayList<>();
        tableModel = new PropertyTaxTableModel(propertyList);
        propertyTable = new JTable(tableModel);

        connectToDatabase();

        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.75;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Mã số thuế:"), gbc);

        taxIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0.75;
        add(taxIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Tên người đóng thuế:"), gbc);

        ownerNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(ownerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Số mét vuông đất sở hữu:"), gbc);

        areaField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(areaField, gbc);
       
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(new JLabel("Nhập vào mã số thuế cần tìm:"), gbc);
        
        searchField = new JTextField(30); 
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3; 
        add(searchField, gbc);

        addButton = new JButton("Thêm");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(addButton, gbc);

        editButton = new JButton("Sửa");
        editButton.setPreferredSize(new Dimension(100, 30));
        editButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(editButton, gbc);

        deleteButton = new JButton("Xóa");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(deleteButton, gbc);
        

        searchButton = new JButton("Tìm kiếm");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(propertyTable), gbc);

        loadPropertyData();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLythuenhadat;" +
                    "encrypt=true;trustServerCertificate=true;" +
                    "trustStore=yourTrustStore;trustStorePassword=yourTrustStorePassword";
            String user = "sa";
            String password = "123456789";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối");
        }
    }

    private void loadPropertyData() {
        try {
            String query = "SELECT * FROM PropertyTax";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String taxId = rs.getString("taxId");
                String ownerName = rs.getString("ownerName");
                double area = rs.getDouble("area");
                PropertyTax propertyTax = new PropertyTax(taxId, ownerName, area);
                propertyList.add(propertyTax);
            }
            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addProperty();
        } else if (e.getSource() == editButton) {
            editProperty();
        } else if (e.getSource() == deleteButton) {
            deleteProperty();
        } else if (e.getSource() == searchButton) {
            searchProperty();
        }
    }

    private void addProperty() {
        try {
            String taxId = taxIdField.getText();
            String ownerName = ownerNameField.getText();
            double area = Double.parseDouble(areaField.getText());

            if (taxId.isEmpty() || ownerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập đầy đủ thông tin.");
                return;
            }

            String query = "INSERT INTO PropertyTax (taxId, ownerName, area) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, taxId);
            pstmt.setString(2, ownerName);
            pstmt.setDouble(3, area);
            pstmt.executeUpdate();

            PropertyTax propertyTax = new PropertyTax(taxId, ownerName, area );
            propertyList.add(propertyTax);
            tableModel.fireTableDataChanged();
            clearFields();
            JOptionPane.showMessageDialog(this, "Đã thêm thành công.");
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi.");
        }
    }

    private void clearFields() {
        taxIdField.setText("");
        ownerNameField.setText("");
        areaField.setText("");
        searchField.setText("");
    }

    private void editProperty() {
        int selectedRow = propertyTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String taxId = taxIdField.getText();
                String ownerName = ownerNameField.getText();
                double area = Double.parseDouble(areaField.getText());

                if (taxId.isEmpty() || ownerName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Hãy nhập đầy đủ thông tin.");
                    return;
                }

                String query = "UPDATE PropertyTax SET ownerName = ?, area = ? WHERE taxId = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, ownerName);
                pstmt.setDouble(2, area);
                pstmt.setString(3, taxId);
                pstmt.executeUpdate();

                PropertyTax property = propertyList.get(selectedRow);
                property.setTaxId(taxId);
                property.setOwnerName(ownerName);
                property.setArea(area);
                tableModel.fireTableDataChanged();
                clearFields();
                JOptionPane.showMessageDialog(this, "Đã sửa thành công.");
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa.");
        }
    }

    private void deleteProperty() {
        int selectedRow = propertyTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String taxId = propertyList.get(selectedRow).getTaxId();
                String query = "DELETE FROM PropertyTax WHERE taxId = ?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, taxId);
                pstmt.executeUpdate();

                propertyList.remove(selectedRow);
                tableModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(this, "Đã xóa thành công.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi.");
            }
        } else {
            JOptionPane.showMessageDialog(this, " Chọn một dòng để xóa.");
        }
    }

     private void searchProperty() {
                    String searchId = searchField.getText();
                    for (PropertyTax property : propertyList) {
                        if (property.getTaxId().equals(searchId)) {
                            taxIdField.setText(property.getTaxId());
                            ownerNameField.setText(property.getOwnerName());
                            areaField.setText(String.valueOf(property.getArea()));
                            JOptionPane.showMessageDialog(this, "Đã tìm thấy.");
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Không tìm thấy mã số thuế.");
                }

                public static void main(String[] args) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            PropertyTaxManager frame = new PropertyTaxManager();
                            frame.setVisible(true);
                        }
                    });
                }
            }

    

   
        
