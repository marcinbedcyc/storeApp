package magazyn;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddItemPanel extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JButton add, cancel;
    private JTextField name, count, price;
    private JLabel lName, lCount, lPrice;
    public Boolean isCorrectInput, isInput;
    private Produkt temp;
    
    public AddItemPanel(JFrame owner){
        super(owner, "Dodanie produktu", true);
        setSize(300, 250);
        setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);

        lName = new JLabel("Nazwa");
        lName.setBounds(10, 20, 100, 20);
        lName.setVisible(true);
        add(lName);

        name = new JTextField();
        name.setToolTipText("Podaj nazwe produktu");
        name.setBounds(120, 20, 140, 20);
        name.setVisible(true);
        add(name);

        lCount = new JLabel("Ilosc");
        lCount.setBounds(10, 60, 100, 20);
        lCount.setVisible(true);
        add(lCount);

        count = new JTextField();
        count.setToolTipText("Podaj ilosc produktu");
        count.setBounds(120, 60, 140, 20);
        count.setVisible(true);
        add(count);
        
        lPrice = new JLabel("Cena");
        lPrice.setBounds(10, 100, 100, 20);
        lPrice.setVisible(true);
        add(lPrice);

        price = new JTextField();
        price.setToolTipText("Podaj cene detaliczna produktu");
        price.setBounds(120, 100, 140, 20);
        price.setVisible(true);
        add(price);
        

        add = new JButton("Dodaj");
        add.setBounds(30, 150, 100, 20);
        add.setVisible(true);
        add.addActionListener(this);
        add(add);

        cancel = new JButton("Anuluj");
        cancel.setBounds(170, 150, 100, 20);
        cancel.setVisible(true);
        cancel.addActionListener(this);
        add(cancel);
    }

    public Produkt getNewProdukt(){
        return temp;
    }

    public Boolean isOk(){
        if(isCorrectInput && isInput)
            return true;
        else
            return false;
    }

    public void setFocus(){
        name.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        isInput = isCorrectInput = false;

        if(!price.getText().equals("") || !name.getText().equals("") || !count.getText().equals("")){
            isInput = true;
        }   

        if(src == add){
            if(!isInput){
                JOptionPane.showMessageDialog(this, "Prosze uzupelnic dane lub wyjsc", "Nieuzupelnione dane",JOptionPane.WARNING_MESSAGE);
            }
            else if(isInput){
                try{
                    temp = new Produkt(Double.parseDouble(price.getText()), Integer.parseInt(count.getText()), name.getText());
                    isCorrectInput = true;
                }
                catch(NumberFormatException eNumberFormatException){
                    JOptionPane.showMessageDialog(this, "Prosze podac liczbe", "Bledne dane",JOptionPane.WARNING_MESSAGE);
                    isCorrectInput = false;
                }
                catch(NullPointerException nullPointerException){
                    JOptionPane.showMessageDialog(this, "Prosze podac liczbe nlp", "Bledne dane",JOptionPane.WARNING_MESSAGE);
                    isCorrectInput = false;
                }
            }
            if(isInput && isCorrectInput){
                setVisible(false);
            }
        }
        else if(src == cancel){
            setVisible(false);
        }
    }

}