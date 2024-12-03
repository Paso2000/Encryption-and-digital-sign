package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;

public class View extends JFrame{
    private File selectedFile;
    private JTextArea textArea = new JTextArea();
    private JComboBox<String> comboCipher;
    private JComboBox<String> comboHash;
    private JMenuItem cipher;
    private JMenuItem decipher;
    private JMenuItem protegerFileWithHash;
    private JMenuItem verificarFileHash;
    private JMenuItem protegerMessageWithHash;
    private JMenuItem verificarMessageHash;
    private JMenuItem Exit;
    private JPanel passwordPanel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JLabel labelCipher;
    private JLabel labelHash;





    public View() {
        // Crea il frame
        JFrame frame = new JFrame("Practice 3 of SRT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Crea la barra dei menu
        JMenuBar menuBar = new JMenuBar();


        // Crea il menu
        JMenu menuFile = new JMenu("File");
        JMenu menuOption = new JMenu("Option");
        menuBar.add(menuFile);
        menuBar.add(menuOption);


        // Crea le voci di menu
        cipher = new JMenuItem("Cipher");
        cipher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        decipher = new JMenuItem("Decipher");
        decipher.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));

        protegerFileWithHash = new JMenuItem("Protect with hash");
        protegerFileWithHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));


        verificarFileHash = new JMenuItem("Verify hash");
        verificarFileHash.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        protegerMessageWithHash = new JMenuItem("Protect message with hash");
        verificarMessageHash = new JMenuItem("Verify message hash");

        Exit = new JMenuItem("Exit");
        Exit.addActionListener(e -> System.exit(0));

        // Aggiungi le voci di menu al menu "Fichero"
        menuFile.add(cipher);
        menuFile.add(decipher);
        menuFile.add(protegerFileWithHash);
        menuFile.add(verificarFileHash);
        menuFile.add(protegerMessageWithHash);
        menuFile.add(verificarMessageHash);
        menuFile.addSeparator(); // Aggiunge una linea di separazione
        menuFile.add(Exit);
        labelCipher = new JLabel("Algorithm Cipher");
        comboCipher = new JComboBox<>(new String[] {
                "PBEWithMD5AndDES", "PBEWithMD5AndTripleDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40"
        });
        comboCipher.setSelectedItem("PBEWithMD5AndDES");


        // Componenti per Algoritmo Hash/HMac
        labelHash = new JLabel("Algorithm Hash/HMac");
        comboHash = new JComboBox<>(new String[] {
                "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512",
                "HmacMD5", "HmacSHA1", "HmacSHA256", "HmacSHA384", "HmacSHA512"
        });
        comboHash.setSelectedItem("MD2");

        JMenuItem algorithm = new JMenuItem("Algorithm");
        algorithm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Crea e mostra il dialogo di configurazione
                JDialog dialog = new JDialog(frame, "Option", true);
                dialog.setLayout(new GridLayout(4, 1, 10, 10));
                dialog.setSize(300, 200);
                dialog.setLocationRelativeTo(frame);

                // Componenti per Algoritmo Cifrado



                // Aggiungi i componenti al dialogo
                dialog.add(labelCipher);
                dialog.add(comboCipher);
                dialog.add(labelHash);
                dialog.add(comboHash);
                // Rendi visibile il dialogo
                dialog.setVisible(true);
            }
        });
        menuOption.add(algorithm);

        passwordPanel = new JPanel(new BorderLayout());
        passwordLabel = new JLabel("Value: ");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        textArea.setLineWrap(true); // Permette l'andata a capo automatica
        textArea.setWrapStyleWord(true); // Andata a capo sui confini delle parole

        // Aggiungi la JTextArea a uno JScrollPane per abilitarne lo scorrimento

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.setLayout(new BorderLayout());
        frame.setJMenuBar(menuBar);
        frame.add(passwordPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);


    }
    public String getInputText() {
        return textArea.getText();
    }

    public String getHashValue() {
        System.out.println(new String(passwordField.getPassword()));
        return new String(passwordField.getPassword());
    }

    public String getSymmetricAlgorithm() {
        return (String) comboCipher.getSelectedItem();}

    public String getHashAlgorithm(){return (String) comboHash.getSelectedItem(); }


    // Methods to view output
    public void addResult(String result) {
        textArea.append(result+"\n\n");
    }
    public void setResult(String result) {
        textArea.setText(result);
    }
    // button listener
    public void addEncryptButtonListener(ActionListener listener) {
        cipher.addActionListener(listener);
    }

    public void addDecryptButtonListener(ActionListener listener) {
        decipher.addActionListener(listener);
    }

    public void addVerifyButtonListener(ActionListener listener) {
        verificarMessageHash.addActionListener(listener);
    }

    public void addMessageHashButtonListener(ActionListener listener) {
        protegerMessageWithHash.addActionListener(listener);
    }
    public void addFileHashButtonListener(ActionListener listener) {
        protegerFileWithHash.addActionListener(listener);
    }
    public void addVerifyFileHashButtonListener(ActionListener listener) {
        verificarFileHash.addActionListener(listener);
    }

    // Methods to view error message
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    public static File getFile() {
        // Crea un JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Mostra il dialogo di selezione file
        int returnValue = fileChooser.showOpenDialog(null);

        // Verifica l'azione dell'utente
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Restituisci il file selezionato
            return fileChooser.getSelectedFile();
        }

        // Nessun file selezionato
        return null;
    }
}