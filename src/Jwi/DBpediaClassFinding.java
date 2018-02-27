/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jwi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import static Jwi.JWI.v6Counter;
import static Jwi.JWI.v6;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author WIN8
 */
public class DBpediaClassFinding {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author WIN8
 */

 
    
 //------------------------semantic synset--------------------------------------------------------------   
   public static int Hypernym(IWord s1,String s2,int senseNum) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "../../../myprog/libSemSimilarity/Jwnl/WordNet/3.0/dict/" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > hypernyms =synset . getRelatedSynsets ( Pointer . HYPERNYM );
    List <IWord > words ;
    for( ISynsetID sid : hypernyms ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
           // if (word.contains(s2)) sw=1;
            if (word.toString()!=null)
            {
                 v6.add(v6Counter,word.toString());v6Counter++;
            }
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
 //-------------------------------------------------------------------------------------    
    
    
 public static String compareKeywordWithClasses(String keyWord) throws IOException{ 
  //System.out.println(new File("../../../myprog/QueryLib/INOut/dBpediaClassesLevel1-hypernym.txt")
  //  .getCanonicalPath());
     String dBpediaClassesLevel1Hypernym = "../../../myprog/QueryLib/INOut/dBpediaClassesLevel1-hypernym.txt";
    String lineInputFile2="";         
    String[] Temp;
    try {   
        FileReader fileReader2 = new FileReader(dBpediaClassesLevel1Hypernym);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        while((lineInputFile2 = bufferedReader2.readLine()) != null) {
            Temp=    lineInputFile2.split("  ");
            String Class=Temp[0];
            Temp[0]=ReadFromDBPediaClassesFile.removeFirstSpace(Temp[0]);
            Temp[0]= ReadFromDBPediaClassesFile.replaceCapitals(Temp[0]);
            if (Temp[0].contentEquals(keyWord))
                return lineInputFile2;           
        }   //End read lines from text     
        bufferedReader2.close();      
    }  
    catch(FileNotFoundException ex) {
                 System.out.println("Unable to open file '" +dBpediaClassesLevel1Hypernym + "'");                
    }
    catch(IOException ex) {
                 System.out.println("Error reading file '"+ dBpediaClassesLevel1Hypernym+ "'");                  
    }
            //---------------------------end read Input File2----an--------------
    //System.out.println(new File(".").getCanonicalPath());
            String dBpediaClassesLevel2Hypernym = "../../../myprog/QueryLib/INOut/dBpediaClassesLevel2-hypernym.txt";
    lineInputFile2="";         
   try {   
        FileReader fileReader2 = new FileReader(dBpediaClassesLevel2Hypernym);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        while((lineInputFile2 = bufferedReader2.readLine()) != null) {
            Temp=    lineInputFile2.split("  ");
            String Class=Temp[0];
            Temp[0]=ReadFromDBPediaClassesFile.removeFirstSpace(Temp[0]);
            Temp[0]= ReadFromDBPediaClassesFile.replaceCapitals(Temp[0]);
            if (Temp[0].contentEquals(keyWord))
                return lineInputFile2;           
        }   //End read lines from text     
        bufferedReader2.close();      
    }  
    catch(FileNotFoundException ex) {
                 System.out.println("Unable to open file '" +dBpediaClassesLevel1Hypernym + "'");                
    }
    catch(IOException ex) {
                 System.out.println("Error reading file '"+ dBpediaClassesLevel1Hypernym+ "'");                  
    }
            //---------------------------end read Input File2----an--------------
            
 return "Null";
 }  
 
    public static int compareHypernymsWithClasses(){
 //-----------------read from DBPedia Classes File-------------
    String InputFile2 = "../../../myprog/QueryLib/INOut/dBpediaClassesLevel1-hypernym.txt";
    String lineInputFile2="";         
    String[] Temp;
    try {   
                    FileReader fileReader2 = new FileReader(InputFile2);
                    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
                    while((lineInputFile2 = bufferedReader2.readLine()) != null) {
                       Temp=    lineInputFile2.split("  ");
                       for(int i=0;i<JWI.v6Counter;i++){
                          if (JWI.v6.get(i).toString().contentEquals(Temp[0]))
                             return 1;
                        }//while
                     //  for(int i=0;i<JWI.v3Counter;i++){
                     //     if (JWI.v3.get(i).toString().contentEquals(Temp[0]))
                      //       return 1;
                      //  }//for
                    }   //End read lines from text     
                   bufferedReader2.close();      
                 }  
                 catch(FileNotFoundException ex) {
                 System.out.println("Unable to open file '" +InputFile2 + "'");                
                 }
                 catch(IOException ex) {
                 System.out.println("Error reading file '"+ InputFile2 + "'");                  
                 }
            //---------------------------end read Input File2----an--------------
            //--------- print result of comparision in output anf file------------- 
 return 0;
 }
 
 public static String main(String keyWord) throws FileNotFoundException, IOException {
 //String keyWord="weapon";
 String word2="";
 int classFoundSign=0;
 String Class="Null";
 v6Counter=0;
 int t=0;
 int signV6Counter=0;
  
 while(Class.contentEquals("Null")) {  
       
     if(v6Counter!=0){
            keyWord=JWI.v6.get(signV6Counter).toString();
            signV6Counter++;
     }
    else if(t!=0) return "Null";
    t=1;
     Class=compareKeywordWithClasses(keyWord);
     return Class;
    /*if (!Class.contentEquals("Null")|| keyWord=="entity"){
         return Class;
    }
    else{
       
        String wnhome = System . getenv (" WNHOME ");
        String path = wnhome + File . separator + " dict ";
        URL url = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
        IDictionary dict = new Dictionary ( url);
        dict . open ();
       
        IIndexWord idxWord = dict . getIndexWord (keyWord, POS.VERB);
        if(idxWord==null)
           idxWord = dict . getIndexWord (keyWord, POS.NOUN);
        if(idxWord==null)
           idxWord = dict . getIndexWord (keyWord, POS.ADJECTIVE);
           if(idxWord!=null)
           for(int i=0;i<idxWord.getWordIDs().size();i++){
            
                   IWordID wordID = idxWord . getWordIDs ().get (i) ;
                   IWord word = dict . getWord ( wordID );
                   ISynset synset = word . getSynset ();
                 
                   double hypernym=Hypernym(word,word2,idxWord.getWordIDs().size()); System.out.println(hypernym);
           
            }
         
        
        }*/
 }
return "Null";
 }   
}


