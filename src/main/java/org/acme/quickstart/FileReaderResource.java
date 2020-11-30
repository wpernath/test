package org.acme.quickstart;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;

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
        File f = new File(path + "/" + fileName);
        LOG.info("Reading from " + f.getAbsolutePath());
        
        try {
            String txt = "There is no text file at '" + f.getAbsolutePath() + "'! A new file will be created.";
            if( !f.exists()) {
                LOG.info(f.getAbsolutePath() + " does not exist");
                Files.writeString(f.toPath(), txt);
            }
            else {
                LOG.info("File exists. Reading and returning contents.");                
                txt = Files.readString(f.toPath());
                txt += "\nNew Access at: " + new Date(System.currentTimeMillis());
                Files.writeString(f.toPath(), txt);
            }
            return txt;
        }
        catch(IOException e) {
            return e.toString();
        }
    }

}
