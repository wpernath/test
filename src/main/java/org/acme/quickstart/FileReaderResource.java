package org.acme.quickstart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/reader")
public class FileReaderResource {

    private static final Logger LOG = Logger.getLogger(FileReaderResource.class);

    @ConfigProperty(name = "reader.path")
    String path;

    @ConfigProperty(name = "reader.fileName")
    String fileName;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String reader() {
        LOG.info("Reading from " + path + "/" + fileName);

        try {
            String txt = Files.readString(Paths.get(path + "/" + fileName));
            return txt;    
        }
        catch(IOException e) {
            return e.toString();
        }
    }

}
