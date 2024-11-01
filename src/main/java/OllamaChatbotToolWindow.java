import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class OllamaChatbotToolWindow{
    private final JPanel panel;
    private final OllamaApiClient api = new OllamaApiClient();
    public JComponent getContent()
    {
        return this.panel;
    }

    public OllamaChatbotToolWindow()
    {

        panel = new JPanel(new BorderLayout());

        JTextArea inputArea = new JTextArea();
        inputArea.setText("Write your question here");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton sendButton = new JButton("Send");
        JPanel south = new JPanel(new BorderLayout());
        JPanel button = new JPanel(new FlowLayout());

        sendButton.addActionListener(e -> {
            String prompt = inputArea.getText();
            inputArea.setText("");
            outputArea.setText("Generating...");
            Thread t = new JTextAreaUpdater(outputArea, new String[]{"Generating.", "Generating..", "Generating..."});
            t.start();
            SwingWorker<String, Void> worker = new SwingWorker<>() {
                @Override
                protected String doInBackground() throws Exception {
                    return api.queryOllamaModel("llama3.2", prompt, "");
                }

                @Override
                protected void done() {
                    String response;
                    try {
                        t.interrupt();
                        response = get();
                    } catch (InterruptedException ex) {
                        response = ("Error: " + ex.getMessage());
                    } catch (ExecutionException ee) {
                        response= ("Error during execution: " + ee.getCause().getMessage());
                    }
                    outputArea.setText(response);
                    outputArea.setCaretPosition(0);
                }
            };
            worker.execute();
        });

        south.add(new JScrollPane(inputArea),BorderLayout.NORTH);
        button.add(sendButton);
        south.add(button,BorderLayout.SOUTH);



        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        panel.add(south,BorderLayout.SOUTH);


    }
}
