package com.bitmonlab.core.assembler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Abstract class that provide the common behavior of the assemblers.
 * 
 * @param <T>
 *            the data transfer object.
 * @param <K>
 *            the entity to transform.
 */
public class SimpleAssembler<T, K> implements Assembler<T, K> {

    private Class<T> dtoClass;
    private Class<K> applicationClass;

    public SimpleAssembler(Class<T> dtoClass, Class<K> applicationClass) {
	this.dtoClass = dtoClass;
	this.applicationClass = applicationClass;
    }

    
    public T createDataTransferObject(K application) throws AssemblyException {
	T transferObject;

	try {
	    transferObject = dtoClass.newInstance();
	    BeanUtils.copyProperties(transferObject, application);
	} catch (IllegalAccessException e) {
	    throw new AssemblyException(e);
	} catch (InstantiationException e) {
	    throw new AssemblyException(e);
	} catch (InvocationTargetException e) {
	    throw new AssemblyException(e);
	}

	return transferObject;
    }

    
    public Collection<T> createDataTransferObjects(Collection<K> entities) throws AssemblyException {
	Collection<T> dataTransferObjects = new ArrayList<T>();
	for (K entity : entities) {
	    dataTransferObjects.add(createDataTransferObject(entity));
	}

	return dataTransferObjects;
    }

   
    public K createDomainObject(T dataTransferObject) throws AssemblyException {
	K application;

	try {
	    application = applicationClass.newInstance();
	    BeanUtils.copyProperties(application, dataTransferObject);

	} catch (IllegalAccessException e) {
	    throw new AssemblyException(e);
	} catch (InstantiationException e) {
	    throw new AssemblyException(e);
	} catch (InvocationTargetException e) {
	    throw new AssemblyException(e);
	}

	return application;
    }

   
    public Collection<K> createDomainObjects(Collection<T> dataTransferObjects) throws AssemblyException {
	Collection<K> domainObjects = new ArrayList<K>();
	for (T dto : dataTransferObjects) {
	    domainObjects.add(createDomainObject(dto));
	}

	return domainObjects;
    }
}
