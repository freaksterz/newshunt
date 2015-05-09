package com.newshack.util;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by freakster on 9/5/15.
 */
public class NLPUtil {

    /**
     * This is custom tokenizer class
     * @param content
     * @return
     * @throws Exception
     */
    public ArrayList<String> tokenizer(String content) throws Exception
    {
        ArrayList<String> lines = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("en-sent.bin").getFile());
        FileInputStream fileInputStream = new FileInputStream(file);
        SentenceModel model = new SentenceModel(fileInputStream);
        SentenceDetectorME detectorME = new SentenceDetectorME(model);
        String[] sent = detectorME.sentDetect(content);

        for (String string : sent)
        {
            lines.add(string);
        }

        fileInputStream.close();
        return lines;
    }

    static String stopWordArray[];
    static{
        String stopWords="a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,it's,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your,very,too";
        stopWordArray=stopWords.split(",");
        Arrays.sort(stopWordArray);
    }





    /**
     * This method will remove the stop words from content which are not relevant for searching
     * @author : freakster
     * @param content The content of the each feed
     * @return Filtered content for each lines
     * @throws Exception
     */
    public static String getStopWordRemoval(String content) throws Exception {

        //sample has to be read from database
        content=content.replaceAll(",", "");
        content=content.replaceAll("!", "");
        content=content.replaceAll("@", "");
        content=content.replaceAll("#", "");
        content=content.replaceAll("\\$", "");
        content=content.replaceAll("%", "");
        content=content.replaceAll("^", "");
        content=content.replaceAll("&", "");
        content=content.replaceAll("\\*", "");
        content=content.replaceAll("\\(", "");
        content=content.replaceAll("\\)", "");
        content=content.replaceAll("\\[", "");
        content=content.replaceAll("\\]", "");
        content=content.replaceAll("\\{", "");
        content=content.replaceAll("\\}", "");
        content=content.replaceAll("\\:", "");
        content=content.replaceAll("\\?", "");
        content=content.replaceAll("\\;", "");
        content=content.replaceAll("\\-", "");
        content=content.replaceAll("\"", "");
        content=content.replaceAll("\\“", "");
        content=content.replaceAll("\\`", "");
        content=content.replaceAll("^[0-9]*", "");

        String sample_wostop="";

        ArrayList<String> samp= new NLPUtil().tokenizer(content);

        for (String string : samp) {

            String words_sent[]=string.split(" ");
            String x="";
            for (String string2 : words_sent) {
                string2=string2.toLowerCase();

                int found=Arrays.binarySearch(stopWordArray, string2);
                if(found<0){
                    x=x+" "+string2.trim();
                }
            }
            if(x.trim().length()>0)
                sample_wostop=sample_wostop+x+"||";
        }

        return sample_wostop;

    }


}
