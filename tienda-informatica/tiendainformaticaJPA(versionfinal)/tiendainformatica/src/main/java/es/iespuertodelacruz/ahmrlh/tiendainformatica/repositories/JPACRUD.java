package es.iespuertodelacruz.ahmrlh.tiendainformatica.repositories;

import java.util.List;

public interface JPACRUD<T,E> {
	
	List<T> findAll();
	
	T findById(E id);
	
	T save(T obj);
	
	T update(T obj);
	
	boolean delete(E id);

}
