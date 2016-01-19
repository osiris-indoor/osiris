package com.bitmonlab.osiris.core.assembler;

import java.util.Collection;

/**
 * Class that convert a dto in a business entity.
 *
 * @param <T> the class of the data transfers object.
 * @param <K> the class of the business entity.
 */
public interface Assembler<T,K> {
	
	/**
	 * Method that create a data transfer object from a entity.
	 * @param entity the entity to transform.
	 * @return the data transfer object to send.
	 * @throws AssemblyException 
	 */
	T createDataTransferObject(K entity) throws AssemblyException;
	
	/**
	 * Transform a list of applications in a list of dtos.
	 * @param applications the applications.
	 * @return the list of dtos.
	 * @throws AssemblyException 
	 */
	Collection<T> createDataTransferObjects(Collection<K> applications) throws AssemblyException;
	
	/**
	 * Create a domain object from a dto.
	 * @param dataTransferObject the object that receives from the remote invoker.
	 * @return the entity.
	 */
	K createDomainObject(T dataTransferObject)  throws AssemblyException;
	
	/**
	 * Transform a list of dtos in a list of domain objects.
	 * @param dataTransferObjects the Data Transfer Objects List
	 * @return a list of Domain objects 
	 * 
	 */
	Collection<K> createDomainObjects(Collection<T> dataTransferObjects) throws AssemblyException;
	
}