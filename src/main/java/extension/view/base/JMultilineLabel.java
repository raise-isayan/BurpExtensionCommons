package extension.view.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author isayan
 */
public class JMultilineLabel extends JTextArea {

    public JMultilineLabel (){
        this(null, 0, 0);
    }

    public JMultilineLabel (String text){
        this(text, 0, 0);
    }

    public JMultilineLabel (String text,int rows,int columns){
        super(text, rows, columns);
        setWrapStyleWord(true);
        setBorder(null);
        setOpaque(false);
        setEditable(false);
    }

    public static void main(String args[]) {
        JMultilineLabel label = new JMultilineLabel("First\nSecont\nThird:");
        final JTextField text = new JTextField(20);
        JButton b = new JButton("popup");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                label.setText("");
            }
        });
        JPanel p = new JPanel();
        p.add(label);
        p.add(text);
        p.add(b);
        final JFrame f = new JFrame();
        f.getContentPane().add(p);
        f.pack();
        f.setVisible(true);
    }

}
