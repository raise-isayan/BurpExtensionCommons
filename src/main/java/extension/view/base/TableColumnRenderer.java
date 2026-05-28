package extension.view.base;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
/**
 *
 * @author isayan
 */
public class TableColumnRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!table.isEnabled()) {
            c.setBackground(SystemColor.controlShadow); // 無効時の背景色
            c.setForeground(SystemColor.textInactiveText);       // 無効時の文字色
        } else {
            // 有効時は選択状態に応じた色を設定
            if (isSelected) {
                c.setBackground(table.getSelectionBackground());
                c.setForeground(table.getSelectionForeground());
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
        }
        return c;
    }
}