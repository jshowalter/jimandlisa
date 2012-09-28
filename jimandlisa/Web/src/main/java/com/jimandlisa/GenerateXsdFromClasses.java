package com.jimandlisa;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;

public class GenerateXsdFromClasses {
	
    private static class Resolver extends SchemaOutputResolver {
    	
        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
              File file = new File(suggestedFileName);
              StreamResult result = new StreamResult(file);
              result.setSystemId(file.toURI().toURL().toString());
              return result;
        }
    }

	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws JAXBException, IOException {
        Class<?>[] classes = new Class[] {Invoice.class, InvoiceFilter.class}; // PUT CLASS(ES) HERE
        JAXBContext jaxbContext = JAXBContext.newInstance(classes);
        SchemaOutputResolver resolver = new Resolver();
        jaxbContext.generateSchema(resolver);
	}
}
