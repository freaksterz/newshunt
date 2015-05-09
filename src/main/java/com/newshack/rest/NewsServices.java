package com.newshack.rest;

import com.newshack.corelogic.NewsContent;
import org.json.JSONArray;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * This class exposes services consumes json and return json String.
 * Created by freakster on 9/5/15.
 */

@Path("/content")
public class NewsServices {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String getRelatedContent(String req )
    {
        NewsContent newsContent = new NewsContent();

        List<Map> mapList= newsContent.getContent(req);

        //convert it back to json and send
        JSONArray array = new JSONArray(mapList);

        return array.toString();
    }


}
