package org.example.BusinessLogic.validators;

/**
 * Interfata Validator este o interfata generica utilizata pentru validarea unui obiect de tip T.
 * @param <T> tipul obiectului care va fi validat
 */
public interface Validator<T> {
	/**
	 * Metoda este folosita pentru a valida obiectul de tipul T
	 * @param t obiectul ce va fi validat
	 */
	public void validate(T t);
}
