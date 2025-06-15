package org.tomcatlogwatcher.userinterface.adapters;

import org.tomcatlogwatcher.data.Constants;
import org.tomcatlogwatcher.utility.DateUtil;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.MouseEvent;
import java.util.Date;

public class AccessLogTableMouseAdapter extends MouseInputAdapter {
    private static final int RESIZE_MARGIN = 5;
    private int resizingRow = -1;
    private int startY;
    private boolean resizing = false;

    private final JTable table;

    public AccessLogTableMouseAdapter(JTable table) {
        this.table = table;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);

        if (row == -1 || col == -1) return;

        // Handle row resizing
        if (isInResizeZone(e, row)) {
            resizing = true;
            resizingRow = row;
            startY = e.getY();
            return;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            copyCell(row, col);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        resizing = false;
        resizingRow = -1;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (resizing && resizingRow >= 0) {
            int newHeight = table.getRowHeight(resizingRow) + (e.getY() - startY);
            newHeight = Math.max(newHeight, 20); 
            table.setRowHeight(resizingRow, newHeight);
            startY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Window window = SwingUtilities.getWindowAncestor(table);
        int row = table.rowAtPoint(e.getPoint());
        if (row >= 0 && isInResizeZone(e, row)) {
            window.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        } else {
            window.setCursor(Cursor.getDefaultCursor());
        }
    }

    private boolean isInResizeZone(MouseEvent e, int row) {
        Rectangle rowBounds = table.getCellRect(row, 0, true);
        return e.getY() >= rowBounds.y + rowBounds.height - RESIZE_MARGIN;
    }

    private void copyCell(int row, int col) {
        Object value = table.getValueAt(row, col);
        if (value != null) {
            String copyValue = "";
            if(value instanceof Date){
                String dateFormat = Constants.DATE_FORMATS.COMMON_LOGGING_FORMAT.getValue();
                copyValue = DateUtil.convertDateToString((Date) value, dateFormat);
            } else {
                copyValue = value.toString();
            }
            StringSelection stringSelection = new StringSelection(copyValue);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            JOptionPane.showMessageDialog(null, "Copied: " + copyValue, "Copy", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
