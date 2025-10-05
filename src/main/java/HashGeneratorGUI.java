import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Security;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class HashGeneratorGUI {
    
    private static String inputString;
    private static String filePath;
    
	public static void main(String[] args) {

        Security.addProvider(new BouncyCastleProvider());
        
		HashGenerator md5Generator = new HashGenerator("MD5");
		HashGenerator sha256Generator = new HashGenerator("SHA-256");
		HashGenerator sha512Generator = new HashGenerator("SHA-512");
		HashGenerator ripemd160Generator = new HashGenerator("RIPEMD160");
		HashGenerator whirlpoolGenerator = new HashGenerator("Whirlpool");

       
        JFrame frame = new JFrame("Cryptographic Hash Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());  
        frame.setSize(1200, 1200);
        
        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel);

        // Add Input Label to Left
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;
        gbc.insets.left = 285;
        gbc.anchor = GridBagConstraints.WEST; 
        JLabel inputLabel  = new JLabel("Input");
        panel.add(inputLabel, gbc);
        
        // Add Input TextArea 
        gbc.gridy++;
        gbc.insets.left = 0;
        JTextArea inputTextArea = new JTextArea(20, 50);
        panel.add(scrollableTextArea(inputTextArea), gbc);
        
        // Add File Browse Button
        gbc.gridy++;
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;
 
        JButton fileBrowseButton = new JButton("Select File");
        panel.add(fileBrowseButton, gbc);

        // Add Calculate Hash Button for input text
        JButton calculateHashButton = new JButton("Calculate Hash");
        gbc.insets.top = 10;
        gbc.insets.bottom = 10;
        gbc.insets.left = 110;
        panel.add(calculateHashButton, gbc);

        
        // Add Result Label
        gbc.gridy++;
        gbc.insets.left = 0;
        JLabel resultLabel = new JLabel();
        panel.add(resultLabel, gbc);

        // Add Result TextArea
        gbc.gridy++;
        JTextArea resultTextArea = new JTextArea(15, 60);
        panel.add(scrollableTextArea(resultTextArea), gbc);

        // Set frame to visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        
        // Add action listener to calculateHash button
        fileBrowseButton.addActionListener(new ActionListener() {

        	@Override
            public void actionPerformed(ActionEvent e) {
                // Create a file chooser
                JFileChooser fileChooser = new JFileChooser();

                // Show open dialog
                int result = fileChooser.showOpenDialog(frame);

                // Check if a file was selected
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Set the selected file path to the text field
                    filePath = selectedFile.getAbsolutePath();
                    
                    // clear the input text field
                    inputTextArea.setText(null);
                    
                	String[] parts = filePath.split("/");
                	String fileName = parts[parts.length - 1];
                	
                	resultLabel.setText("Result for file: " + fileName);
                	resultTextArea.setText("");
                	resultTextArea.append("MD5 Hash: " + md5Generator.generateHashForFile(filePath) + "\n\n");
                	resultTextArea.append("SHA-256 Hash: " + sha256Generator.generateHashForFile(filePath) + "\n\n");
                	resultTextArea.append("SHA-512 Hash: " + sha512Generator.generateHashForFile(filePath) + "\n\n");
                	resultTextArea.append("RIPEMD-160 Hash: " + ripemd160Generator.generateHashForFile(filePath) + "\n\n");
                	resultTextArea.append("Whirlpool Hash: " + whirlpoolGenerator.generateHashForFile(filePath) + "\n\n");
                }
            }
        });
        
        // Add action listener to calculateHash button
        calculateHashButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	inputString = inputTextArea.getText();

                if (!inputString.isEmpty()) {
                	resultLabel.setText("Result for Text: ");
                	resultTextArea.setText("");
                	resultTextArea.append("MD5 Hash: " + md5Generator.generateHash(inputString) + "\n\n");
                	resultTextArea.append("SHA-256 Hash: " + sha256Generator.generateHash(inputString) + "\n\n");
                	resultTextArea.append("SHA-512 Hash: " + sha512Generator.generateHash(inputString) + "\n\n");
                	resultTextArea.append("RIPEMD-160 Hash: " + ripemd160Generator.generateHash(inputString) + "\n\n");
                	resultTextArea.append("Whirlpool Hash: " + whirlpoolGenerator.generateHash(inputString) + "\n\n");
                }
            }
        });
	}
	
	private static JScrollPane scrollableTextArea(JTextArea textArea) {
        // Create JTextArea
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Create JScrollPane and add JTextArea to it
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add JScrollPane to the panel
        return scrollPane;
	}
}
