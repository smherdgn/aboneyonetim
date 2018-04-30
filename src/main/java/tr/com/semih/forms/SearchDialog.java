package tr.com.semih.forms;

import tr.com.semih.classes.beans.Abone;
import tr.com.semih.classes.logging.AbstractLogger;
import tr.com.semih.classes.logging.Logger;
import tr.com.semih.classes.properties.Exporter;
import tr.com.semih.classes.properties.Global;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton btnExport;
    private JTextField txtAboneNo;
    private JButton btnSearch;
    private JPanel panelTable;
    private JLabel lblCount;
    private JTable table;
    private JFileChooser chooser;
    private String choosertitle;
    private List<Abone> foundedList = new ArrayList<Abone>();

    private AbstractLogger loggerChain = Logger.getChainOfLoggers(this.getClass());

    public SearchDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        List<String[]> values = new ArrayList<String[]>();
        for (Abone abone : Global.getAboneList()) {

            values.add(abone.toArray());

        }

        table = new JTable();
        panelTable.setLayout(new BorderLayout());
        panelTable.add(new JScrollPane(table), BorderLayout.CENTER);
        panelTable.add(table.getTableHeader(), BorderLayout.NORTH);
        panelTable.setVisible(true);
        panelTable.setSize(200, 200);


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtAboneNo.getText().length() == 9) {
                    DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
                    foundedList.clear();
                    defaultTableModel.setColumnIdentifiers(Abone.getFieldNames().toArray());
                    int count = 0;
                    for (Abone abone : Global.getAboneList()) {
                        if (txtAboneNo.getText().equals(abone.getAboneNumarası())) {
                            foundedList.add(abone);
                            defaultTableModel.addRow(abone.toArray());
                            count++;
                        }
                    }
                    lblCount.setText("Toplam Bulunan Borç Sayısı: " + count);
                    table.setModel(defaultTableModel);
                } else {
                    JOptionPane.showMessageDialog(null, "Abone Numarasını kontrol edip tekrar deneyiniz!");
                }
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                if (foundedList.size() > 0) {
                    chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("."));
                    chooser.setDialogTitle(choosertitle);
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    chooser.setAcceptAllFileFilterUsed(false);

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                        loggerChain.logMessage(AbstractLogger.INFO, "getSelectedFile() : " + chooser.getSelectedFile());
                        StringBuilder html = new StringBuilder("<!DOCTYPE html>").append("\n");
                        html.append("<html>").append("\n")
                                .append("<meta charset=\"UTF-8\">").append("\n")
                                .append("<title>Abone Yöentim Sistemi</title>").append("\n")
                                .append("</head>").append("\n")
                                .append("<body>").append("\n")
                                .append("<table style=\"width:100%\" border=1>").append("\n")

                                .append(Abone.getHtmlHeaders()).append("\n");

                        for (Abone abone : foundedList) {
                            html.append(abone.getHtmlRow()).append("\n");
                        }

                        html.append("</table>").append("\n")
                                .append("</body>").append("\n")
                                .append("</html>").append("\n");

                        String fileName = chooser.getSelectedFile() + File.separator + "ExportedAbone.html" + new Date().getTime();
                        Exporter exporter = new Exporter(html, fileName);
                        if (exporter.export()) {
                            JOptionPane.showMessageDialog(null, "Export İşlemi Tamamlandı");
                        } else {

                            JOptionPane.showMessageDialog(null, "Export İşlemi Hatalı Sonuçlandı");
                        }
                    } else {

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Export Edilebilecek Bir Hiç Data Bulunamadı");
                }
            }
        });
    }

    private void onOK() {

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        SearchDialog dialog = new SearchDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
