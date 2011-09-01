package it.gas.foolslide.desktop.viewer.comics;

import it.gas.foolslide.desktop.engine.persistence.Comic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

public class ComicCellRenderer extends JPanel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	private JLabel image, name;
	private JTextArea description;

	public ComicCellRenderer() {
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		image = new JLabel("lololol", JLabel.CENTER);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 2;
		add(image, c);
		
		name = new JLabel("Name");
		Font tmp = name.getFont();
		name.setFont(new Font(tmp.getName(), Font.BOLD, tmp.getSize()));
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(name, c);
		
		description = new JTextArea();
		description.setEditable(false);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setOpaque(false);
		tmp = description.getFont();
		description.setFont(new Font(tmp.getName(), Font.ITALIC, tmp.getSize()));
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		add(description, c);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Comic c = (Comic) value;
		image.setText("");
		image.setIcon(new ImageIcon(c.getThumb()));
		name.setText(c.getName());
		description.setText(c.getDescription());
		if (isSelected)
			setBackground(Color.BLUE);
		else
			this.setBackground(Color.WHITE);
		return this;
	}

}
