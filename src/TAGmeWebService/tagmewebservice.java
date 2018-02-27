/*
 *In this code we transfer all of pages in apecial wikipedia category to sql table 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TAGmeWebService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.Scanner;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import static parsertree.Parsertree.POStagwordPre;
import static parsertree.Parsertree.POSwordPre;
import static parsertree.Parsertree.POSwordPositionEndPre;
import static parsertree.Parsertree.POSwordPositionStartPre;
import static parsertree.Parsertree.wordsNumber;
//import getWikiPages.DBClass;
/**
 *
 * @author WIN8
 */
public class tagmewebservice {

    /**
     * @param args the command line arguments
     */
    public static String [][] WikiLinksInformationArray= new String [50][5];  //Phrase,phraseStart,phraseEnd,WikiLinkPhrase,number of words
    public static int NumTagMeLinks;
    public static final String SpecialExport_URL = "https://tagme.d4science.org/tagme/tag?";
    public static Document OtherCategories(String text)throws IOException {
                String searchCategory="";
                String FirstCategory="";
                String[] subStr;//----   /*	
               //  String text="Which buildings in art deco style did Shreve, Lamb and Harmon design?";
                 String Query=SpecialExport_URL + "lang=en" +
                    "&gcube-token=" +  URLEncoder.encode("7358841c-821c-4bac-8a9b-4ff7ea704363-843339462", "utf-8")
                   + "&text=" + URLEncoder.encode(text, "utf-8");
                 Document doc =Jsoup.connect(Query).validateTLSCertificates(false).ignoreContentType(true).get();
                 return doc;
                              
                
     }        
     public static int  phraseIsContainSomeSpecialWords(String phrase,String Start,String End){
     if(phrase.matches("I")||phrase.matches("you")||phrase.matches("she")||phrase.matches("he")||
             phrase.matches("we")||phrase.matches("they")||phrase.matches("number")||phrase.matches("numbers")||
             phrase.matches("hundreds")||phrase.matches("milions")
             ||phrase.contains("I ")||phrase.contains("you ")||phrase.contains("he ")||phrase.contains("she ")||phrase.contains("they ")
             )
          return 1;
    return 0;
      }
    public static int  phraseIsContainWhWord(String phrase,String Start,String End){
    if (phrase.contains("Who")||phrase.contains("Where")||phrase.contains("When")||phrase.contains("How many")||phrase.contains("Which")||phrase.contains("What")||phrase.contains("How old")||
            phrase.contains("who")||phrase.contains("where")||phrase.contains("when")||phrase.contains("how many")||phrase.contains("which")||phrase.contains("what")||phrase.contains("how old"))
           return 1;
    return 0;
    }
     public static int  phraseIsCapital(String phrase,String Start,String End){
      
         if(phrase.startsWith("A")||phrase.startsWith("B")||phrase.startsWith("C")
           ||phrase.startsWith("A")||phrase.startsWith("A")||phrase.startsWith("C")
           ||phrase.startsWith("D")||phrase.startsWith("E")||phrase.startsWith("F")
           ||phrase.startsWith("G")||phrase.startsWith("H")||phrase.startsWith("I")
           ||phrase.startsWith("J")||phrase.startsWith("K")||phrase.startsWith("L")
           ||phrase.startsWith("M")||phrase.startsWith("N")||phrase.startsWith("O")
           ||phrase.startsWith("P")||phrase.startsWith("Q")||phrase.startsWith("R")
           ||phrase.startsWith("S")||phrase.startsWith("T")||phrase.startsWith("U")
           ||phrase.startsWith("V")||phrase.startsWith("W")||phrase.startsWith("X")
           ||phrase.startsWith("Y")||phrase.startsWith("Z"))
               if(   !(Integer.parseInt(Start)==0 &  !POStagwordPre[1].matches("NNP")))
             return 1;
   return 0;
    }
   public static int  phraseEndsWithPrep(String phrase,String Start,String End){
       if(phrase.endsWith(" in")||phrase.endsWith(" at")||phrase.endsWith(" of")||phrase.endsWith(" under")||
          phrase.endsWith(" on")||phrase.endsWith(" by")||phrase.endsWith(" against")||phrase.endsWith(" during")||     
          phrase.endsWith(" with")||phrase.endsWith(" to")||phrase.endsWith(" from")||phrase.endsWith(" during")
               )
         return 1;
         return 0;
    }
   
   public static int phraseIsAVerb(String phrase,String Start,String End){
     int phraseStart=0;
     int phraseEnd=0;
     phraseStart=Integer.parseInt(Start);
     phraseEnd=Integer.parseInt(End);
     
    
      for(int i=1;i<wordsNumber;i++){
        if(POSwordPre[i].matches(phrase) & (POSwordPositionStartPre[i]>=phraseStart) & (POSwordPositionEndPre[i]<=phraseEnd)
                &
                (POStagwordPre[i].matches("VB")||POStagwordPre[i].matches("VBG")||POStagwordPre[i].matches("VBN")
                ||POStagwordPre[i].matches("VBP")
                ||POStagwordPre[i].matches("VBD")||POStagwordPre[i].matches("VBZ")
                )
                ) {
                 return 1;            
               }
        }
        return 0;
     }
    public static int AreTwoOverlappedLinksType1(int NumTagMeLinks,String currentPhraseStart,String currentPhraseEnd){//common end or overlapped in middle so second link should be neglected
        int curLinkStart=Integer.parseInt(currentPhraseStart);
        int curLinkEnd=Integer.parseInt(currentPhraseEnd);
        if(NumTagMeLinks>=1){
        int previousLinkStart=Integer.parseInt(WikiLinksInformationArray[NumTagMeLinks-1][1]);
        int previusLinkEnd=Integer.parseInt(WikiLinksInformationArray[NumTagMeLinks-1][2]);
        if (curLinkEnd==previusLinkEnd ||curLinkStart<previusLinkEnd)
                   return 1;
        }
    return 0;
    }    
    public static int AreTwoOverlappedLinksType2(int NumTagMeLinks,String currentPhraseStart,String currentPhraseEnd){//common start so first link should be neglected
        int curLinkStart=Integer.parseInt(currentPhraseStart);
        int curLinkEnd=Integer.parseInt(currentPhraseEnd);
        if(NumTagMeLinks>=1){
        int previousLinkStart=Integer.parseInt(WikiLinksInformationArray[NumTagMeLinks-1][1]);
        int previusLinkEnd=Integer.parseInt(WikiLinksInformationArray[NumTagMeLinks-1][2]);
        if (curLinkStart==previousLinkStart)
                   return 1;
        }
    return 0;
    }
public static int phraseIsContainedNumber(String phrase,String Start,String End){
     int phraseStart=0;
     int phraseEnd=0;
     phraseStart=Integer.parseInt(Start);
     phraseEnd=Integer.parseInt(End);
     
      String[] str=phrase.split(" ");
      
        for(int i=1;i<wordsNumber;i++){
          if((str[0].contains(POSwordPre[i])) & (POSwordPositionStartPre[i]>=phraseStart) & (POSwordPositionEndPre[i]<=phraseEnd)
                &
                (POStagwordPre[i].matches("CD")
                )
                ) 
                 return 1;            
               
         }
     
        return 0;
     }
    public static void Initialization(){
                  
                   NumTagMeLinks=0;
                   for(int i=0;i<50;i++){
                      WikiLinksInformationArray[i][0]="";
                      WikiLinksInformationArray[i][1]="";
                      WikiLinksInformationArray[i][2]="";
                      WikiLinksInformationArray[i][3]="";
                     
                   }
                  
   }
    public static void docComposition(Document doc){
     String mid1,phrase,phraseStart,phraseEnd,wikiLinkPhrase;
      int numberOfWordsInPhrase;
     String str =doc.text();
        System.out.print(doc);
             String[] content=str.split("\"id\"");
             for(int i=1;i<content.length;i++){
              String node=content[i];
              String[] mid=node.split("\",\"start\":");
              if(mid.length==1){//its title is similar with previous id in doc
                  phrase=mid[0].split(",\"spot\":\"")[1].split("\"}")[0];
                  
                  
                  numberOfWordsInPhrase=phrase.split(" ").length;
                  if(phrase.contains(","))
                  numberOfWordsInPhrase=numberOfWordsInPhrase+phrase.split(",").length-1;
                  phraseStart=mid[0].split(",\"link_probability\":")[0].split("start\":")[1].split("\"")[0];
                  phraseEnd=mid[0].split(",\"spot\":\"")[0].split("\"end\":")[1];
                  wikiLinkPhrase=WikiLinksInformationArray[NumTagMeLinks-1][0];
                          
              }else{
              phrase=mid[1].split(",\"spot\":\"")[1].split("\"}")[0];
              numberOfWordsInPhrase=phrase.split(" ").length;
              if(phrase.contains(","))
                 numberOfWordsInPhrase=numberOfWordsInPhrase+phrase.split(",").length-1;
              phraseStart=mid[1].split(",\"link_probability\":")[0];
              phraseEnd=mid[1].split(",\"spot\":\"")[0].split("\"end\":")[1];
              wikiLinkPhrase=mid[0].split("\"title\":\"")[1];
              }
              if(phrase.endsWith(" ")){
                      phrase=phrase.substring(0, phrase.length() - 1);
                      phraseEnd=Integer.toString(Integer.parseInt(phraseEnd)-1);
                  }
              if(phrase.endsWith(" 's")){
                 phrase=phrase.replace(" 's", "");
                 phraseEnd=Integer.toString(Integer.parseInt(phraseEnd)-3);
                 numberOfWordsInPhrase--;
              }   
              if (phraseIsContainedNumber(phrase,phraseStart,phraseEnd)==0 & phraseIsAVerb(phrase,phraseStart,phraseEnd)==0 & phraseIsContainWhWord(phrase,phraseStart,phraseEnd)==0
                     & phraseEndsWithPrep(phrase,phraseStart,phraseEnd)==0 & phraseIsContainSomeSpecialWords(phrase,phraseStart,phraseEnd)==0
                      & phraseIsCapital(phrase,phraseStart,phraseEnd)==1){
                if(AreTwoOverlappedLinksType2(NumTagMeLinks,phraseStart,phraseEnd)==0 & AreTwoOverlappedLinksType1(NumTagMeLinks,phraseStart,phraseEnd)==0) {
                  WikiLinksInformationArray[NumTagMeLinks][0]=phrase;
                   WikiLinksInformationArray[NumTagMeLinks][1]=phraseStart;
                   WikiLinksInformationArray[NumTagMeLinks][2]=phraseEnd;
                   WikiLinksInformationArray[NumTagMeLinks][3]=wikiLinkPhrase;
                   WikiLinksInformationArray[NumTagMeLinks][4]=String.valueOf(numberOfWordsInPhrase);
                   System.out.println(" ");
                   System.out.print(phrase);
                   System.out.print("--");
                   System.out.println(wikiLinkPhrase);
                   NumTagMeLinks++;
                }
                else if(AreTwoOverlappedLinksType2(NumTagMeLinks,phraseStart,phraseEnd)==1){
                 NumTagMeLinks--;      
                   WikiLinksInformationArray[NumTagMeLinks][0]=phrase;
                   WikiLinksInformationArray[NumTagMeLinks][1]=phraseStart;
                   WikiLinksInformationArray[NumTagMeLinks][2]=phraseEnd;
                   WikiLinksInformationArray[NumTagMeLinks][3]=wikiLinkPhrase;
                   WikiLinksInformationArray[NumTagMeLinks][4]=String.valueOf(numberOfWordsInPhrase);
                   System.out.println(" ");
                   System.out.print(phrase);
                   System.out.print("--");
                   System.out.print(" ");
                   System.out.println(wikiLinkPhrase);
                   NumTagMeLinks++;  
                }
              }
                
                  }
    } 
    
     public static void Correcttion(String text){
     
       for(int i=0;i<NumTagMeLinks;i++){
         for(int j=1;j<wordsNumber;j++)
         {
           if(POSwordPositionStartPre[j]>=Integer.parseInt(WikiLinksInformationArray[i][1])&
              POSwordPositionStartPre[j]<=Integer.parseInt(WikiLinksInformationArray[i][2]) &
              POSwordPositionEndPre[j]>Integer.parseInt(WikiLinksInformationArray[i][2])
                   ){ WikiLinksInformationArray[i][0]="";
                    for(int k=Integer.parseInt(WikiLinksInformationArray[i][1]);k<=POSwordPositionEndPre[j]-1;k++){
                        WikiLinksInformationArray[i][0]=WikiLinksInformationArray[i][0]+text.charAt(k);
                    }
                    
                    WikiLinksInformationArray[i][2]=Integer.toString(POSwordPositionEndPre[j]);
           
                     }
         }
       }
     }
    public static void main(String text) throws IOException {
	      Initialization();     
              Document doc= OtherCategories(text);
              docComposition(doc);
           
              Correcttion(text);
              System.out.print(doc);
    }
   
}



