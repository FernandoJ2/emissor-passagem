package br.unip.team.emissorpassagem.model.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import br.unip.team.emissorpassagem.model.entidade.Horario;
import br.unip.team.emissorpassagem.model.entidade.Itinerario;

public class ItinerarioDAO {
	private static final Logger LOGGER = Logger.getLogger(ItinerarioDAO.class.getName());
	private static final String INSERT_ITINERARIO = "insert into Itinerario(IdEstacaoEmbarque,IdHorarioEmbarque,IdEstacaoDesembarque,QtdPassagem,PrecoPassagem) values(?,?,?,?,?)";
	private static final String VALIDA_ESTACAOHORARIO = "select 1 as existe from EstacaoHorario where idEstacao = ? and idHorario = ?";
	private static final String UPDATE_ITENARARIO = "update Itinerario set IdEstacaoEmbarque= ?,IdHorarioEmbarque = ?,IdEstacaoDesembarque = ?,QtdPassagem = ? where id = ?";
	private static final String HORARIO_ID = "select id, Hora from Horario where Hora = ?";
	private static final String SELECT_ITINERARIO_POR_ID = "select IdEstacaoEmbarque, IdHorarioEmbarque, IdEstacaoDesembarque, QtdPassagem, PrecoPassagem from Itinerario where id = ?";

	public int adicionar(Itinerario obj) {
		String[] idRetornado = { "id" };

		try (Connection conexao = ConnectionFactory.conexaoSQLServer();
				PreparedStatement pstmt = conexao.prepareStatement(INSERT_ITINERARIO, idRetornado);) {
			pstmt.setInt(1, obj.getEstacaoEmbarque().getId());
			pstmt.setInt(2, obj.getEmbarqueHorario().getId());
			pstmt.setInt(3, obj.getEstacaoDesembarque().getId());
			pstmt.setInt(4, obj.getQtdPassagem());
			pstmt.setDouble(5, obj.getPreco());

			if (pstmt.executeUpdate() == 0) {
				throw new SQLException("Insert falhou, nenhuma linha afetada.");
			}
			pstmt.getGeneratedKeys();

			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			LOGGER.info("Erro na query SQL " + INSERT_ITINERARIO);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return 0;
	}

	public boolean validaRelacionamento(int idEstacao, int idHorario) {
		try (Connection conexao = ConnectionFactory.conexaoSQLServer();
				PreparedStatement pstmt = conexao.prepareStatement(VALIDA_ESTACAOHORARIO);) {
			pstmt.setInt(1, idEstacao);
			pstmt.setInt(2, idHorario);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next())
					return true;
			}
			return false;
		} catch (SQLException e) {
			LOGGER.info("Erro na query SQL " + VALIDA_ESTACAOHORARIO);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return false;
	}

	public boolean alterarItinerario(Itinerario obj) {
		try (Connection conexao = ConnectionFactory.conexaoSQLServer();
				PreparedStatement pstmt = conexao.prepareStatement(UPDATE_ITENARARIO);) {
			pstmt.setInt(1, obj.getEstacaoEmbarque().getId());
			pstmt.setInt(2, obj.getEmbarqueHorario().getId());
			pstmt.setInt(3, obj.getEstacaoDesembarque().getId());
			pstmt.setInt(4, obj.getQtdPassagem());
			pstmt.setDouble(5, obj.getPreco());
			return pstmt.execute();
		} catch (SQLException e) {
			LOGGER.info("Erro na query SQL " + UPDATE_ITENARARIO);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return false;
	}

	public int obterIdHorario(String string) {
		try (Connection conexao = ConnectionFactory.conexaoSQLServer();
				PreparedStatement pstmt = conexao.prepareStatement(HORARIO_ID);) {
			pstmt.setString(1, string);
			try (ResultSet rs = pstmt.executeQuery();) {
				Horario horario = new Horario("Hora");
				while (rs.next()) {
					horario.setId(rs.getInt("Id"));
				}
				return horario.getId();
			}
		} catch (SQLException e) {
			LOGGER.info("Erro na query SQL");
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return 0;
	}

	public Itinerario obterPorId(int id) {
		try (Connection conexao = ConnectionFactory.conexaoSQLServer();
				PreparedStatement pstmt = conexao.prepareStatement(SELECT_ITINERARIO_POR_ID);) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				Itinerario itinerario = new Itinerario();

				while (rs.next()) {
					itinerario.getEstacaoEmbarque().setId(rs.getInt("IdEstacaoEmbarque"));
					itinerario.getEmbarqueHorario().setId(rs.getInt("IdHorarioEmbarque"));
					itinerario.getEstacaoDesembarque().setId(rs.getInt("IdEstacaoDesembarque"));
					itinerario.setQtdPassagem(rs.getInt("QtdPassagem"));
					itinerario.setPreco(rs.getDouble("PrecoPassagem"));
				}
				return itinerario;
			}
		} catch (SQLException e) {
			LOGGER.info("Erro na query SQL"+ SELECT_ITINERARIO_POR_ID);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		}
		return null;
	}

}
