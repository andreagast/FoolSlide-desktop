package it.gas.foolslide.desktop.viewer.comics;

import it.gas.foolslide.desktop.engine.persistence.Comic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.miginfocom.swing.MigLayout;

public class ComicCellRenderer extends JPanel implements ListCellRenderer {
	private static final long serialVersionUID = 1L;
	private JLabel image, name, description;
	private Toolkit tk;

	public ComicCellRenderer() {
		tk = Toolkit.getDefaultToolkit();
		initComponents();
	}
	
	private void initComponents() {
		//setOpaque(true);
		setLayout(new MigLayout("debug"));
		
		image = new JLabel("lololol", JLabel.CENTER);
		add(image, "spany 2");
		
		name = new JLabel("Name");
		Font tmp = name.getFont();
		name.setFont(new Font(tmp.getName(), Font.BOLD, tmp.getSize()));
		add(name, "wrap");
		
		description = new JLabel("Description");
		tmp = description.getFont();
		description.setFont(new Font(tmp.getName(), Font.ITALIC, tmp.getSize()));
		add(description);
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Comic c = (Comic) value;
		try {
			Image img = tk.getImage(new URL(c.getThumb_url()));
			img = img.getScaledInstance(-1, 125, Image.SCALE_FAST);
			image.setIcon(new ImageIcon(img));
			image.setText("");
		} catch (MalformedURLException e) {
			image.setIcon(null);
			image.setText("???");
		}
		name.setText(c.getName());
		description.setText(c.getDescription());
		if (isSelected)
			setBackground(Color.BLUE);
		else
			this.setBackground(Color.WHITE);
		return this;
	}

}
