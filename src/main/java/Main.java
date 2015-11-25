import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 *
 */
public class Main {

    private JFrame frame;
    private JTextField txtPath;

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main(){
        setUpFrame();
    }

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

    public void dataProcessing(String filename){

        HashSet<String> essencesSet = new HashSet<>();

        /*try(FileWriter writer = new FileWriter(filename, false)) {

            Random random = new Random();

            for(int i = 0; i < 10000; i++) {
                int random1 = random.nextInt(100);
                int random2 = random.nextInt(100);
                writer.write(random1 + " " + random2);
                if(i != 9999) {
                    writer.write("\r\n");
                }
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }*/

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
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    essencesSet.remove(String.valueOf(secondEssenceId + "," + firstEssenceId));

                } else {
                    essencesSet.add(firstEssenceId + "," + secondEssenceId);
                }
            }
        }catch (Exception exception){
            System.out.println("Something went wrong. Check input data.");
        }
    }
}