package magazyn;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RemoveItemPanel extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField name;
    private JLabel lName;
    private JButton bConfim, bCancel;
    private String removeItemName;
    private Boolean canceled;

    public RemoveItemPanel(JFrame owner) {
        super(owner, "Usuwanie produktu", true);
        setSize(300, 200);
        setLayout(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

        lName = new JLabel("Nazwa");
        lName.setBounds(10, 40, 100, 20);
        lName.setVisible(true);
        add(lName);

        name = new JTextField();
        name.setToolTipText("Podaj nazwe produktu");
        name.setBounds(120, 40, 140, 20);
        name.setVisible(true);
        add(name);

        bConfim = new JButton("Potwierdz");
        bConfim.setBounds(30, 110, 100, 20);
        bConfim.setVisible(true);
        bConfim.addActionListener(this);
        add(bConfim);

        bCancel = new JButton("Anuluj");
        bCancel.setBounds(170, 110, 100, 20);
        bCancel.setVisible(true);
        bCancel.addActionListener(this);
        add(bCancel);
    }

    public void setFocus(){
        name.requestFocusInWindow();
    }

    public String getRemoveItemName(){
        return removeItemName;
    }

    public Boolean isCanceled(){
        return canceled;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        canceled = false;

        if(source == bConfim){
            removeItemName = name.getText();
            setVisible(false);
        }
        else{
            canceled = true;
            setVisible(false);
        }
    }
}