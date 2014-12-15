package br.com.lelak.teste.persistence.dao;

import java.util.List;

import br.com.lelak.teste.model.Instrument;
import br.com.lelak.teste.model.User;

public interface InstrumentDAO extends DAO<Instrument> {

	byte[] findImageById(Long id);

	List<Instrument> listByUser(User user);

	
}
