package br.unip.team.emissopassagem.view.Telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import br.unip.team.emissopassagem.model.entidade.Estacao;
import br.unip.team.emissopassagem.model.persistencia.EstacaoDAO;

public class TelaEstacaoDb extends Tela {

	public TelaEstacaoDb(JPanel basePane, JPanel backPane) {
		setBasePane(basePane);
		setBackPane(backPane);
		setNewPane(window(), basePane);
	}

	@Override
	public JPanel window() {
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(corDeFundo);
		
		setLabel(contentPane, "Escolha a estação de destino", 180, 100, 500, 30, 20);				
		JComboBox <Object> cb = setComboBox(contentPane, 170, 150, 300, 60);
		setCbEstacao(cb);
		JButton prox = setButtonProx(contentPane);
		JButton cancel = setButtonCancel(contentPane);
		
		//Event Listener
		
		prox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				TelaPassagem telaPassagem = new TelaPassagem(basePane, backPane);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				backPane.setVisible(true);
			}
		});
		
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cb.getSelectedItem() != null) {
					prox.setEnabled(true);
				}
			}
		});
		
		return contentPane;
		
	}
	
	public void setCbEstacao (JComboBox<Object> cb) {		
		EstacaoDAO estacaoDAO = new EstacaoDAO();
		for(Estacao estacao: estacaoDAO.obterTodos()) {
			cb.addItem(estacao);
		}		
	}

}
