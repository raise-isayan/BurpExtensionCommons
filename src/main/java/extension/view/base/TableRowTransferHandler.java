package extension.view.base;

import extension.view.base.CustomTableModel;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

public class TableRowTransferHandler extends TransferHandler {

    private final static Logger logger = Logger.getLogger(TableRowTransferHandler.class.getName());

    protected static final DataFlavor DATA_FLAVOR = new DataFlavor(List.class, "List of Messages");

    private JTable table;
    private CustomTableModel tableModel;

    private List<Integer> selectedModelIndexs = List.of();
    private int dropModelIndex;

    public TableRowTransferHandler() {
        super();
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        this.table = (JTable) c;
        this.tableModel = (CustomTableModel) this.table.getModel();
        this.selectedModelIndexs = Arrays.stream(this.table.getSelectedRows())
                .mapToObj(selectedRowIndex -> this.table.convertRowIndexToModel(selectedRowIndex))
                .collect(Collectors.toList());

        final List<Object[]> selectedRows = this.selectedModelIndexs.stream()
                .map(this.tableModel::getRows)
                .collect(Collectors.toList());

        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DATA_FLAVOR};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return DATA_FLAVOR.equals(flavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
                if (isDataFlavorSupported(flavor)) {
                    return selectedRows;
                }
                throw new UnsupportedFlavorException(flavor);
            }
        };
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        TransferHandler.DropLocation dropLocation = support.getDropLocation();
        if (!(dropLocation instanceof JTable.DropLocation)) {
            return false;
        }

        int dropRawIndex = ((JTable.DropLocation) dropLocation).getRow();
        if (dropRawIndex >= this.tableModel.getRowCount()) {
            this.dropModelIndex = this.tableModel.getRowCount(); // Last
        } else {
            this.dropModelIndex = this.table.convertRowIndexToModel(dropRawIndex);
        }

        try {
            List<Object[]> rows = (List<Object[]>) support.getTransferable().getTransferData(DATA_FLAVOR);
            for (Object[] row : rows) {
                this.tableModel.insertRow(this.dropModelIndex, row);
            }
            int dropViewIndex = this.table.convertRowIndexToView(this.dropModelIndex);
            this.table.getSelectionModel().addSelectionInterval(dropViewIndex, dropViewIndex + rows.size() - 1);
            return true;
        } catch (UnsupportedFlavorException | IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    protected void exportDone(JComponent c, Transferable data, int action) {
        if (action != TransferHandler.MOVE || Objects.isNull(this.selectedModelIndexs)) {
            return;
        }

        final List<Integer> selectedDeleteIndexs = this.selectedModelIndexs.stream()
                .map(selectedModelIndex -> selectedModelIndex < this.dropModelIndex ? selectedModelIndex : selectedModelIndex + this.selectedModelIndexs.size())
                .collect(Collectors.toList());

        for (int i = selectedDeleteIndexs.size() - 1; 0 <= i; i--) {
            this.tableModel.removeRow(selectedDeleteIndexs.get(i));
        }
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return support.isDrop() && support.isDataFlavorSupported(DATA_FLAVOR);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.MOVE;
    }

}
