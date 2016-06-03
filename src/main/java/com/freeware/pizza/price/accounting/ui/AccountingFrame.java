package com.freeware.pizza.price.accounting.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import com.freeware.pizza.price.accounting.calc.IPriceCalculator;
import com.freeware.pizza.price.accounting.calc.PriceCalculator;
import com.freeware.pizza.price.accounting.model.PizzaInfo;
import com.freeware.pizza.price.accounting.model.PizzaPayingPrice;

public class AccountingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private IPriceCalculator calculator = new PriceCalculator();
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountingFrame frame = new AccountingFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AccountingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Fair pizza price accounting");
		
		// menu
		JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);
	    JMenu fileMenu = new JMenu("File");
	    JMenu helpMenu = new JMenu("Help");
	    
	    fileMenu.setMnemonic('F');
	    helpMenu.setMnemonic('H');
	    
	    JMenuItem aboutItem = new JMenuItem("About");
	    JMenuItem exitItem = new JMenuItem("Exit");
	    helpMenu.add(aboutItem);
	    fileMenu.add(exitItem);
	    menuBar.add(fileMenu);
	    menuBar.add(helpMenu);
	    
	    exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	    aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(AccountingFrame.this.getContentPane(), 
						"This program calculates fair pizza price according to the number of free pizzas.\n\n"
						+ "Author: Evgeniy Vishnyakov\n"
						+ "Version: 0.0.1", 
						"About", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	    
		// content
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(new BorderLayout());
				
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.add(createLabels());

		List<PizzaInfoPanel> panels = IntStream.range(1, 8).mapToObj(i -> new PizzaInfoPanel(i)).collect(Collectors.toList());
		panels.forEach(infoPanel::add);
        
		contentPane.add(infoPanel);
		
		JButton button = new JButton("Calculate");
		contentPane.add(button, BorderLayout.PAGE_END);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<PizzaPayingPrice> price = calculator.calculate(panels.stream().map(PizzaInfoPanel::toPizzaInfo).collect(Collectors.toList()));
				for(int i = 0; i < price.size(); i++) {
					panels.get(i).setTotalPrice(price.get(i).getPayingPrice());
				}
			}
		});
	}
	
	private JPanel createLabels() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		JLabel numberLabel = new JLabel("   ", SwingConstants.CENTER); 
		panel.add(numberLabel);
		
		GridBagConstraints c = new GridBagConstraints();
        c.weightx=1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
		
        JLabel text = new JLabel("Name", SwingConstants.CENTER);
		panel.add(text, c);

        JLabel freeCheckbox = new JLabel("Is free?", SwingConstants.CENTER);
		panel.add(freeCheckbox);

        JLabel price = new JLabel("Init price", SwingConstants.CENTER);
		panel.add(price, c);

		JLabel ptotalPrice = new JLabel("Total price", SwingConstants.CENTER);
		panel.add(ptotalPrice, c);
		
		return panel;
	}
	
	private static class PizzaInfoPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;
		private JTextField text;
		private JCheckBox freeCheckbox;
		private JFormattedTextField initPrice;
		private JTextField totalPrice;

		public PizzaInfoPanel(int idx) {
			super(new GridBagLayout());
			JLabel numberLabel = new JLabel(idx + ". ", SwingConstants.CENTER); 
			add(numberLabel);
			
			GridBagConstraints c = new GridBagConstraints();
	        c.weightx=1.0;
	        c.fill = GridBagConstraints.HORIZONTAL;
			
			text = new JTextField(); 
			add(text, c);
			
			freeCheckbox = new JCheckBox();
			add(freeCheckbox);

			NumberFormatter f = new NumberFormatter();
			f.setAllowsInvalid(false);
			initPrice = new JFormattedTextField(f);
			add(initPrice, c);

			totalPrice = new JTextField();
			totalPrice.setEditable(false);
			add(totalPrice, c);
		}
		
		public PizzaInfo toPizzaInfo() {
			PizzaInfo info = new PizzaInfo();
			info.setFree(freeCheckbox.isSelected());
			info.setLabel(text.getText());
			if(initPrice.getText() == null || initPrice.getText().isEmpty()) {
				info.setPrice(BigDecimal.ZERO);
			} else {
				info.setPrice(new BigDecimal(initPrice.getText().replaceAll("\\W", "")));	
			}
			return info;
		}
		
		public void setTotalPrice(BigDecimal price) {
			totalPrice.setText(price.toString());
		}
		
	}

}
