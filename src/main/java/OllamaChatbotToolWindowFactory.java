
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class OllamaChatbotToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        OllamaChatbotToolWindow window = new OllamaChatbotToolWindow();
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(window.getContent(), "Ollama Chatbot", false);
        toolWindow.getContentManager().addContent(content);
    }
}
