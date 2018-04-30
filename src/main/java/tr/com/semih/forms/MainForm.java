package tr.com.semih.forms;

import tr.com.semih.classes.logging.AbstractLogger;
import tr.com.semih.classes.logging.Logger;
import tr.com.semih.classes.properties.FileTypeFilter;
import tr.com.semih.classes.properties.Global;
import tr.com.semih.classes.properties.ThreadFileRead;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainForm {
    private static JFrame frame = null;
    private FileFilter fileFilter;
    private JPanel mainPanel;
    private JButton btnFileSelect;
    private JTextArea txtLog;
    private JButton btnSearch;
    private AbstractLogger loggerChain = Logger.getChainOfLoggers(this.getClass());
    private static BufferedReader bufferedReader;

    public MainForm() {

        btnFileSelect.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int countOfThread = 20;
                JFileChooser fileChooser = new JFileChooser();
                fileFilter = new FileTypeFilter(".txt", "Text File");
                fileChooser.addChoosableFileFilter(fileFilter);
                int status = fileChooser.showOpenDialog(frame);


                if (status == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();
                    loggerChain.logMessage(AbstractLogger.INFO, "Seçilen Dosya :" + selectedFile.getParent() + "/" + selectedFile.getName());
                    Global.getErrorList().clear();
                    txtLog.setText("");
                    txtLog.append("Seçilen Dosya :" + selectedFile.getParent() + "/" + selectedFile.getName() + "\n");
                    try {
                        bufferedReader = new BufferedReader(new FileReader(selectedFile));
                        List<Thread> threadList = new ArrayList<Thread>();

                        for (int currentThread = 1; currentThread <= countOfThread; currentThread++) {
                            Thread thread = new Thread(new ThreadFileRead(bufferedReader), "A" + currentThread);
                            thread.start();
                            threadList.add(thread);
                        }
                        for (int i = 0; i < threadList.size(); i++) {
                            threadList.get(i).join();
                        }
                        txtLog.append("Toplam Başarılı Yükelenen Abone Sayısı: " + Global.getAboneList().size() + "\n");
                        txtLog.append("Toplam Başarısız  Yükelenen Abone Sayısı: " + Global.getErrorList().size() + "\n");

                        int i = 1;
                        for (Map.Entry entry : Global.getErrorList().entrySet()) {

                            txtLog.append(i + " " + "Hatalı Satır: '" + entry.getKey() + "'  Hata Nedeni: " + entry.getValue() + "\n");
                            i++;
                        }


                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                } else if (status == JFileChooser.CANCEL_OPTION) {
                    loggerChain.logMessage(AbstractLogger.INFO, "IPTAL EDİLDİ");
                }
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Global.getAboneList().size() > 0) {
                    SearchDialog searchDialog = new SearchDialog();

                    searchDialog.setSize(750, 500);
                    searchDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Sistemde Herhanbir Abone Datasına Raslanmadı Yeni Abone Eklemeyi Dene");
                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}

