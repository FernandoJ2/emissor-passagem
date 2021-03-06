package br.unip.team.emissorpassagem.view.tela;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.unip.team.emissorpassagem.view.PanelBase;

public class TelaIniciar extends PanelBase {

	private static final Logger LOGGER = Logger.getLogger(TelaIniciar.class.getName());

	public TelaIniciar(JPanel basePane) {
		setBasePane(basePane);
		setNewPane(window(), basePane);
	}

	public JPanel window() {
		try {

			JPanel contentPane = new JPanel();
			contentPane.setBackground(corDeFundo);

			JButton btnIniciar = new JButton("INICIAR");
			btnIniciar.setBounds(230, 335, 180, 60);
			btnIniciar.setFont(new Font("Arial", Font.BOLD, 25));
			btnIniciar.setBackground(new Color(0, 153, 255));
			btnIniciar.setForeground(Color.white);
			btnIniciar.setOpaque(true);
			btnIniciar.setBorderPainted(false);
			btnIniciar.setFocusPainted(false);
			contentPane.add(btnIniciar);

			contentPane.add(setLabel("Bem Vindo", 165, 70, 350, 60, 60));
			contentPane.add(setLabel("Precione o botão Iniciar para comprar sua passagem", 75, 165, 500, 30, 20));

			ImageIcon img = new ImageIcon(getClass().getResource("..\\assets\\train.png"));
			JLabel imgTrem = new JLabel(img);
			imgTrem.setBounds(0, 0, 640, 280);
			contentPane.add(imgTrem);

			btnIniciar.addActionListener(e -> {
				contentPane.setVisible(false);
				new TelaEstacaoEb(basePane, contentPane);
			});
		
			return contentPane;
			
		} catch (Exception e) {
			LOGGER.info("Erro ao montar painel");
			e.getMessage();
		}
		return null;
	}

	public JLabel setLabel(String str, int x, int y, int l, int a, int size) {

		JLabel lb = new JLabel(str);
		lb.setBounds(x, y, l, a);
		lb.setForeground(Color.white);
		lb.setFont(new Font(lb.getFont().getName(), lb.getFont().getStyle(), size));
		return lb;
	}
}
