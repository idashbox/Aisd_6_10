
import util.SwingUtils;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JButton button1;
    private JTextArea textArea1;



    public FrameMain() {
        this.setTitle("Двоичные деревья");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        button1.addActionListener(actionEvent -> {
            try {
                String directoryPath = "files";
                Map<String, List<String>> fileMap = new HashMap<>();

                DuplicateFileFinder.scanDirectory(new File(directoryPath), fileMap);

                for (List<String> fileList : fileMap.values()) {
                    if (fileList.size() > 1) {
                        textArea1.append("Duplicates found:");
                        textArea1.append("\n");
                        for (String fileName : fileList) {
                            String[] arr = fileName.split("\\.");
                            String[] str = arr[arr.length-2].split("");
                            textArea1.append(str[str.length-1]);
                            textArea1.append(" ");
                        }
                        textArea1.append("\n");
                    }
                }

            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });


    }

}
