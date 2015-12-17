package com.bitmonlab.core.assembler;


/**
 * The exception that send the method of the assemblers.
 *
 */
public class AssemblyException extends Exception{

	private static final long serialVersionUID = -4741488733357237434L;
	
	/**
	 * Default constructor of assembly exception.
	 */
	public AssemblyException(){		
	}
	
	/**
	 * The assembly exception.
	 * @param cause the cause of the exception.
	 */
	public AssemblyException(Exception cause) {
		super(cause);
	}

	public AssemblyException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public AssemblyException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	
}
