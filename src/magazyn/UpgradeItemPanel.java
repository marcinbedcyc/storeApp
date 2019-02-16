package magazyn;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UpgradeItemPanel extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton bUpdate, bCancel, bConfirm;
    private JTextField name, count, price;
    private JLabel lName, lCount, lPrice;
    private JCheckBox chbName, chbCount, chbPrice;
    private StoreData copy;
    public Boolean isCorrectInput, isInput;

    public UpgradeItemPanel(JFrame owner, StoreData strDt) {
        super(owner, "Aktualizacja produktu", true);
        setSize(300, 250);
        setLayout(null);
        copy = strDt;

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

        chbName = new JCheckBox("Edytuj nazwe  ");
        chbName.setBounds(20, 60, 160, 20); 
        chbName.setHorizontalTextPosition(JLabel.LEFT);
        chbName.setVisible(true);
        add(chbName);
        
        chbCount = new JCheckBox("Edytuj ilość    ");
        chbCount.setBounds(20, 90, 160, 20);
        chbCount.setHorizontalTextPosition(JLabel.LEFT);
        chbCount.setVisible(true);
        add(chbCount);

        chbPrice = new JCheckBox("Edytuj cenę    ");
        chbPrice.setBounds(20, 120, 160, 20);
        chbPrice.setHorizontalTextPosition(JLabel.LEFT);
        chbPrice.setVisible(true);
        add(chbPrice);

        lCount = new JLabel("Ilosc");
        lCount.setBounds(10, 60, 100, 20);
        lCount.setVisible(false);
        add(lCount);

        count = new JTextField();
        count.setToolTipText("Podaj ilosc produktu");
        count.setBounds(120, 60, 140, 20);
        count.setVisible(false);
        add(count);
        
        lPrice = new JLabel("Cena");
        lPrice.setBounds(10, 100, 100, 20);
        lPrice.setVisible(false);
        add(lPrice);

        price = new JTextField();
        price.setToolTipText("Podaj cene detaliczna produktu");
        price.setBounds(120, 100, 140, 20);
        price.setVisible(false);
        add(price);
        

        bUpdate = new JButton("Wyszukaj");
        bUpdate.setBounds(30, 150, 100, 20);
        bUpdate.setVisible(true);
        bUpdate.addActionListener(this);
        add(bUpdate);

        bConfirm = new JButton("Zatwierdź");
        bConfirm.setBounds(30, 150, 100, 20);
        bConfirm.setVisible(false);
        bConfirm.addActionListener(this);
        add(bConfirm);

        bCancel = new JButton("Anuluj");
        bCancel.setBounds(170, 150, 100, 20);
        bCancel.setVisible(true);
        bCancel.addActionListener(this);
        add(bCancel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        int index = -1;
        index = copy.findByName(name.getText());

        if(source == bUpdate){
            if(index != -1){
                bUpdate.setVisible(false);
                chbName.setVisible(false);
                chbCount.setVisible(false);
                chbPrice.setVisible(false);

                bConfirm.setVisible(true);
                price.setVisible(true);
                count.setVisible(true);
                lPrice.setVisible(true);
                lCount.setVisible(true);

                price.setText(String.valueOf(copy.store.get(index).getPrice()));
                count.setText(String.valueOf(copy.store.get(index).getCount()));
                
                if(!chbName.isSelected())
                    name.setEditable(false);
                
                if(!chbCount.isSelected())
                    count.setEditable(false);

                if(!chbPrice.isSelected())
                    price.setEditable(false);
            }
            else{
                JOptionPane.showMessageDialog(this, "Nie znaleziono podanego produktu", "Nie zaleziono", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(source == bConfirm){
            if(!price.getText().equals("") || !name.getText().equals("") || !count.getText().equals("")){
                isInput = true;
            } 
            if(isInput){
                try{
                    copy.store.get(index).setName(name.getText());
                    copy.store.get(index).setCount(Integer.parseInt(count.getText()));
                    copy.store.get(index).setPrice(Double.parseDouble(price.getText()));
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
                if(isInput && isCorrectInput){
                    dispose();
                }
            }
        }
        else if(source == bCancel){
            dispose();
        }
    }



}