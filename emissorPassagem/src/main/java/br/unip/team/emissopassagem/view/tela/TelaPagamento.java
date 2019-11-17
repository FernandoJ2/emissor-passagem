package br.unip.team.emissopassagem.view.tela;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TelaPagamento extends Tela<Object> {

	public TelaPagamento(JPanel basePane, JPanel backPane) {
		setBasePane(basePane);
		setBackPane(backPane);
		setNewPane(window(), basePane);
	}

	@Override
	public JPanel window() {

		JPanel contentPane = new JPanel();
		contentPane.setBackground(corDeFundo);

		setLabel(contentPane, "Digite o numero do cartão:", 150, 40, 500, 30, 18);
		setTextField(contentPane, 150, 90, 300, 50, 15);
		setLabel(contentPane, "Digite o PIN:", 150, 185, 500, 30, 18);
		setTextField(contentPane, 150, 230, 100, 50, 3);
		JButton prox = setButtonProx(contentPane);
		JButton cancel = setButtonCancel(contentPane);

		// Event Listener

		prox.addActionListener(e -> {
			contentPane.setVisible(false);
			new TelaEmissao(basePane, backPane);
		});

		cancel.addActionListener(e -> {
			contentPane.setVisible(false);
			backPane.setVisible(true);
		});

		return contentPane;

	}

}