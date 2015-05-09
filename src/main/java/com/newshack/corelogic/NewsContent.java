package com.newshack.corelogic;

import com.newshack.mongoDAO.MongoLabConnection;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.net.UnknownHostException;
import java.util.*;

/**
 * This class is created for the consuming feed and returning content of type News
 * Created by freakster on 10/5/15.
 */
public class NewsContent implements Content
{
    /**
     *
     * @param feed
     * @return relatedContent - relatedContent
     */
    @Override
    public List<Map> getContent(String feed) {

        List<Map> relatedContent= new ArrayList<>();
        try
        {
            Map hm = MongoLabConnection.returnDocument(null);
            Set<String> keys = hm.keySet();
            List<String> keyList = new ArrayList<>();
            String whitespaceTokenizerLine[] = null;

            for(String key: keys)
            {
                try
                {
                    whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(key);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                for(String w : whitespaceTokenizerLine)
                {
                    if(w.equalsIgnoreCase(feed))
                    {
                        keyList.add(key);
                    }
                }
            }

            for(String kl : keyList)
            {
                Map content = new HashMap();
                content.put(kl,hm.get(kl));
                relatedContent.add(content);
            }


        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        return relatedContent;
    }

}
