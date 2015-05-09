package com.newshack.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * This class exposes services consumes json and return json String.
 * Created by freakster on 9/5/15.
 */

@Path("/content")
public class NewsContent {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String getRelatedContent(String req )
    {
        System.out.println("req = [" + req + "]");
        System.out.println("testing");
        return null;
    }


}
