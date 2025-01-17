package QuanLyThueNhaDat;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PropertyTaxTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Mã số thuế", "Tên người đóng thuế", "Diện tích", "Số tiền thuế"};
    private ArrayList<PropertyTax> propertyList;

    public PropertyTaxTableModel(ArrayList<PropertyTax> propertyList) {
        this.propertyList = propertyList;
    }
    @Override
    public int getRowCount() {
        return propertyList.size();
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PropertyTax property = propertyList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return property.getTaxId();
            case 1:
                return property.getOwnerName();
            case 2:
                return property.getArea();
            case 3:
                return property.getTaxAmount(); 
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    public void addPropertyTax(PropertyTax propertyTax) {
        propertyList.add(propertyTax);
        fireTableRowsInserted(propertyList.size() - 1, propertyList.size() - 1);
    }
    public void updatePropertyTax(int index, PropertyTax propertyTax) {
        propertyList.set(index, propertyTax);
        fireTableRowsUpdated(index, index);
    }

    public void removePropertyTax(int index) {
        propertyList.remove(index);
        fireTableRowsDeleted(index, index);
    }
    public PropertyTax getPropertyTax(int rowIndex) {
        return propertyList.get(rowIndex);
    }
}
