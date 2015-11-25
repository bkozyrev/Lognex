import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class TestTask {

    private JFrame frame;
    private JTextField txtPath;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestTask window = new TestTask();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TestTask(){
        setUpFrame();
    }

    /**
     * ����� setUpFrame ��������� �������� ���� � �������
     * � ����� �������� � ������������ ������� ��� ����
     * ������.
     */

    private void setUpFrame() {
        frame = new JFrame();
        frame.setBounds(200, 200, 450, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);

        txtPath = new JTextField();
        txtPath.setBounds(10, 10, 400, 20);
        txtPath.setColumns(10);

        JButton btnBrowse = new JButton("Browse");
        btnBrowse.setBounds(10, 40, 80, 20);

        JButton btnOk = new JButton("Ok");
        btnOk.setBounds(195, 125, 60, 30);

        frame.getContentPane().add(txtPath);
        frame.getContentPane().add(btnBrowse);
        frame.getContentPane().add(btnOk);

        btnBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    txtPath.setText(fileChooser.getSelectedFile().toString());
                }
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtPath.getText().equals("") || !txtPath.getText().endsWith("txt")){
                    JOptionPane.showMessageDialog(null, "Choose a proper file", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    dataProcessing(txtPath.getText());
                }
            }
        });
    }

    /**
     * ����� dataProcessing ��������� �������� ��������� ���������� �����
     * �������������� � ���� filename.
     * ���� ����������� � ���������:
     * ������������ ��������� ��� �������� ��������������� ���������.
     * ����� ������� ���������� ������ �� ����� ����������� �������
     * ����������� ������ � ���������. ���� ������ ������������, ������������
     * ��������������� �����. ���� ��� - ����������� ��������������� �������
     * � ���������.
     * � �������� ��������� ������������ HashSet, ��� ��� ����������� ���������
     * ����� �� �����������, � �����, ������� � ��������
     * ���������� ��������� O(1) (� ����� ������ ������ O(n))
     * ����� ��������� - O(n) (� ����� ������ ������ O(n^2)), ��� n - ����� ����� � �����.
     * @param filename ���� � �����
     */

    private void dataProcessing(String filename){

        HashSet<String> essencesSet = new HashSet<>();

        try {
            System.setIn(new FileInputStream(filename));
        }
        catch (FileNotFoundException exception){
            System.out.println("file not found");
        }

        Scanner scanner = new Scanner(System.in);

        try {
            while (scanner.hasNextLine()) {
                Integer firstEssenceId = scanner.nextInt(),
                        secondEssenceId = scanner.nextInt();

                if (essencesSet.contains(String.valueOf(secondEssenceId + "," + firstEssenceId))) {

                    try {
                        BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));

                        log.write(secondEssenceId + " " + firstEssenceId + " " + secondEssenceId + "\n");
                        log.flush();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    //essencesSet.remove(String.valueOf(secondEssenceId + "," + firstEssenceId));

                } else {
                    essencesSet.add(firstEssenceId + "," + secondEssenceId);
                }
            }
        }catch (Exception exception){
            System.out.println("Something went wrong. Check input data.");
        }
    }
}