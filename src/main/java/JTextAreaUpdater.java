import javax.swing.*;

public class JTextAreaUpdater extends Thread{

    private JTextArea text;
    private String[] texts;
    private int cnt = 0;
    private int period = 500; //ms
    JTextAreaUpdater(JTextArea text, String[] texts){
        this.text = text;
        this.texts = texts;
    }
    JTextAreaUpdater(JTextArea text, String[] texts,int period){
        this.text = text;
        this.texts = texts;
        this.period = period;
    }
    @Override
    public void run() {
        try {
        while(true) {
            sleep(period);
            text.setText(texts[cnt % (texts.length)]);
            cnt++;
            }
        }
        catch (InterruptedException e) {
        }
    }
}
