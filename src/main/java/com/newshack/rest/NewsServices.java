package com.newshack.rest;

import com.newshack.corelogic.NewsContent;
import org.json.JSONArray;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class exposes services consumes json and return json String.
 * Created by freakster on 9/5/15.
 */

@Path("/contents")
public class NewsServices {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String getRelatedContent(String req )
    {
        String[] splited = null;
        List<Map> mapList = new ArrayList<>();
        NewsContent newsContent = new NewsContent();

        if(req.contains(" ")) {
            splited = req.split("\\s+");



            for (String spli : splited) {
                if (newsContent.getContent(spli) != null) {
                    mapList = (newsContent.getContent(req));
                    break;
                }
            }
        }
        else
        {
            mapList = (newsContent.getContent(req));
        }


        //convert it back to json and send
        JSONArray array = new JSONArray(mapList);

        return array.toString();
    }


}
