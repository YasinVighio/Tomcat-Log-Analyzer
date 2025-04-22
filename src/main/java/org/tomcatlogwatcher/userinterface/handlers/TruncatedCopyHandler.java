package org.tomcatlogwatcher.userinterface.handlers;

import org.tomcatlogwatcher.utility.Utils;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

public class TruncatedCopyHandler extends TransferHandler {
    private List<String> textsToRemove = new ArrayList<>();

    public TruncatedCopyHandler(List<String> textsToRemove) {
        this.textsToRemove = textsToRemove;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTable table = (JTable) c;
        int[] selectedRows = table.getSelectedRows();
        int columnCount = table.getColumnCount();

        StringBuilder sb = new StringBuilder();

        for (int row : selectedRows) {
            for (int col = 0; col < columnCount; col++) {
                Object value = table.getValueAt(row, col);
                String cellValue = (value != null) ? value.toString() : "";

                // Apply truncation logic
                if (Utils.areStringsValid(textsToRemove)) {
                    for (String truncatedText : textsToRemove) {
                        if (truncatedText.contains("*")) {
                            String truncateBefore = truncatedText.substring(0, truncatedText.indexOf("*"));
                            if (cellValue.contains(truncateBefore)) {
                                cellValue = cellValue.substring(0, cellValue.indexOf(truncateBefore));
                            }
                        } else {
                            cellValue = cellValue.replace(truncatedText, "");
                        }
                    }
                }

                sb.append(cellValue).append("\t");
            }

            sb.setLength(sb.length() - 1); // remove last tab
            sb.append("\n");
        }

        return new StringSelection(sb.toString());
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY;
    }
}
