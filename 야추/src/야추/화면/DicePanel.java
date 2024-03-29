package 야추.화면;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DicePanel extends JPanel {

	static Dice[] 주사위들;

	DicePanel(Dice[] _주사위들) {
		setLayout(null);
		setBounds(100, 350, 500, 70);

		주사위들 = _주사위들;

		for (int i = 0; i < 주사위들.length; i++) {
			ImageIcon icon = new ImageIcon(getClass().getResource("/images/No.png"));
			주사위들[i].setIcon(icon);
			주사위들[i].setName((i + 1) + "번째");
		}

	}

	void 보여줘() {
		removeAll();
		for (int i = 0; i < 주사위들.length; i++) {
			주사위들[i].setBounds(주사위들[i].x, 0, 주사위들[i].size, 주사위들[i].size);
			add(주사위들[i]);
		}
	}

	public static Dice[] get주사위들() {
		return 주사위들;
	}
}
