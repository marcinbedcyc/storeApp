package magazyn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MagazynFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton sortByName, sortByCount, sortByPrice, addItem, removeItem, updateItem, exit;
	private JScrollPane scrolledData;
	private JTable productsData;
	private DefaultTableModel model;
	private JTableHeader thData;
	private StoreData s1;
	private String[] columnsName = {"Nazwa" , "Ilosc", "Cena"};
	private Dimension dim;
	private AddItemPanel atp;
	private RemoveItemPanel rmItPanel;

	public MagazynFrame() {
		setLayout(null);
		setTitle("Magazyn");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		dim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dim.width/2, 2*dim.height/3);
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/3-this.getSize().height/3);

		String s = new String("src/magazyn/test.txt");
		s1 = new StoreData();    
		s1.readFromFile(s);

		model = new DefaultTableModel(columnsName, 0);
		productsData = new JTable();
		productsData.setModel(model);
		productsData.setDragEnabled(false);
		productsData.setBackground(Color.DARK_GRAY);
		productsData.setForeground(Color.WHITE);
		
		thData = productsData.getTableHeader();
		thData.setBackground(Color.GRAY);
		thData.setForeground(Color.WHITE);
	
		for(Produkt x:s1.store){
			model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
		}

		scrolledData = new JScrollPane(productsData);
		if(20 * s1.store.size() < 2 * dim.height/3 - 70)
			scrolledData.setBounds(20, 20, dim.width/2 - 260, 20 * s1.store.size());
		else
			scrolledData.setBounds(20, 20, dim.width/2 - 260, 2 * dim.height/3 - 70);

		scrolledData.setVisible(true);
		Border empty = BorderFactory.createEmptyBorder();
		scrolledData.setBorder(empty);
		add(scrolledData);

		sortByName = new JButton("Sortuj po nazwie");
		sortByName.setBounds(dim.width/2 - 220, 20, 200, 30);
		sortByName.setVisible(true);
		sortByName.addActionListener(this);
		add(sortByName);

		sortByCount = new JButton("Sortuj po ilosci");
		sortByCount.setBounds(dim.width/2 - 220, 60, 200, 30);
		sortByCount.setVisible(true);
		sortByCount.addActionListener(this);
		add(sortByCount);

		sortByPrice = new JButton("Sortuj po cenie");
		sortByPrice.setBounds(dim.width/2 - 220, 100, 200, 30);
		sortByPrice.setVisible(true);
		sortByPrice.addActionListener(this);
		add(sortByPrice);

		addItem = new JButton("Dodaj");
		addItem.setBounds(dim.width/2 - 220, 140, 200, 30);
		addItem.setVisible(true);
		addItem.addActionListener(this);
		add(addItem);

		removeItem = new JButton("Usun");
		removeItem.setBounds(dim.width/2 - 220, 180, 200, 30);
		removeItem.setVisible(true);
		removeItem.addActionListener(this);
		add(removeItem);

		updateItem = new JButton("Aktualizuj");
		updateItem.setBounds(dim.width/2 - 220, 220, 200, 30);
		updateItem.setVisible(true);
		updateItem.addActionListener(this);
		add(updateItem);

		exit = new JButton("Wyjscie");
		exit.setBounds(dim.width/2 - 220, 260, 200, 30);
		exit.setVisible(true);
		exit.addActionListener(this);
		add(exit);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if(src == sortByName){
			model.getDataVector().removeAllElements();
			s1.sortByName();
			for(Produkt x:s1.store){
				model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
			}
			scrolledData.repaint();
		}
		else if(src == sortByCount){
			model.getDataVector().removeAllElements();
			s1.sortByCount();
			for(Produkt x:s1.store){
				model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
			}
			scrolledData.repaint();
		}
		else if(src == sortByPrice){
			model.getDataVector().removeAllElements();
			s1.sortByPrice();
			for(Produkt x:s1.store){
				model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
			}
			scrolledData.repaint();
		}
		else if(src == addItem){
			if(atp == null)
				atp = new AddItemPanel(this);

			atp.setVisible(true);
			atp.setFocus();

			if(atp.isOk()){
				Produkt tempProdukt = atp.getNewProdukt();
				if(s1.findByName(tempProdukt.getName()) != -1){
					JOptionPane.showMessageDialog(this, "Produkt znajduje sie w magazynie", "W magazynie", JOptionPane.ERROR_MESSAGE);
				}
				else{
					s1.addProdukt(tempProdukt);
					s1.writeToFile("src/magazyn/test.txt");

					model.addRow(new Object []{tempProdukt.getName(), tempProdukt.getCount(), tempProdukt.getPrice()});
					if(20 * s1.store.size()-14 < 2*dim.height/3 - 50)
						scrolledData.setBounds(20, 20, dim.width/2 - 260, 20 * s1.store.size()-14);
					scrolledData.repaint();
				}
			}
		}
		else if(src == removeItem){
			if(rmItPanel == null)
				rmItPanel = new RemoveItemPanel(this);

			rmItPanel.setVisible(true);
			rmItPanel.setFocus();
			String temp1 = rmItPanel.getRemoveItemName();

			if(!rmItPanel.isCanceled()){
				if(s1.removeItem(temp1)){
					model.getDataVector().removeAllElements();
					for(Produkt x:s1.store){
						model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
					}
					scrolledData.repaint();
					JOptionPane.showMessageDialog(this, "Usuwanie zakonczone sukcesem", "Potwierdzenie", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(this, "Nie mozna usunac, brak produktu w magazynie", "Brak produktu", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(src == updateItem){
			UpgradeItemPanel updItPanel = new UpgradeItemPanel(this, s1);
			updItPanel.setVisible(true);

			model.getDataVector().removeAllElements();
			for(Produkt x:s1.store){
				model.addRow(new Object []{x.getName(), x.getCount(), x.getPrice()});
			}
			
			scrolledData.repaint();
		}
		else if(src == exit){
			s1.writeToFile("src/magazyn/test.txt");
			dispose();
		}
	}
}
