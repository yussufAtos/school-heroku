package com.classrooms.exception;

import java.text.MessageFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EcoleException {

	private static PropertiesConfig propertiesConfig;

	@Autowired
	public EcoleException(PropertiesConfig propertiesConfig) {
		EcoleException.propertiesConfig = propertiesConfig;
	}

	public static RuntimeException throwException(String messageTemplate, String... args) {
		return new RuntimeException(format(messageTemplate, args));
	}
	
	  public static RuntimeException throwException(EntityType entityType, ExceptionType exceptionType, String... args) {
		  //System.err.println("RuntimeException 1   " );
		  
		  String messageTemplate = getMessageTemplate(entityType, exceptionType);
		  
		  System.err.println("RuntimeException 1   "+messageTemplate );
	        return throwException(exceptionType, messageTemplate, args);
	    }
	  
	  public static RuntimeException throwExceptionWithId(EntityType entityType, ExceptionType exceptionType, String id, String... args) {
	        String messageTemplate = getMessageTemplate(entityType, exceptionType).concat(".").concat(id);
	        System.err.println("RuntimeException 2   "+messageTemplate );
	        return throwException(exceptionType, messageTemplate, args);
	    }
	  
	  public static RuntimeException throwExceptionWithTemplate(EntityType entityType, ExceptionType exceptionType, String messageTemplate, String... args) {
	        return throwException(exceptionType, messageTemplate, args);
	    }
	  
	  public static class EntityNotFoundException extends RuntimeException {
	        public EntityNotFoundException(String message) {
	            super(message);
	        }
	    }
	  
	  public static class DuplicateEntityException extends RuntimeException {
	        public DuplicateEntityException(String message) {
	            super(message);
	        }
	    }
	  
	    private static RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
	        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
	            return new EntityNotFoundException(format(messageTemplate, args));
	        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
	            return new DuplicateEntityException(format(messageTemplate, args));
	        }
	        return new RuntimeException(format(messageTemplate, args));
	    }
	    private static String getMessageTemplate(EntityType entityType, ExceptionType exceptionType) {
	    	 System.err.println("exceptionType.getValue :"+exceptionType.getValue());
	    	return entityType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
	    }
	    
	    private static String format(String template, String... args) {
	        Optional<String> templateContent = Optional.ofNullable(propertiesConfig.getConfigValue(template));
	        System.err.println(" templateContent 3  "+templateContent);
	       // System.err.println(" templateContent 3  "+templateContent.get());
	        if (templateContent.isPresent()) {
		        System.err.println(" args 5 "+args.toString());
		        System.err.println(" args 5 "+args.length);
		        System.err.println(" MessageFormat.format(templateContent.get(), args) : "+MessageFormat.format(templateContent.get(), args));
	            return MessageFormat.format(templateContent.get(), args);
	        }
	        return String.format(template, args);
	    }

}
