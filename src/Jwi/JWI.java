/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jwi;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author WIN8
 */
public class JWI{

    /**
     * @param args the command line arguments
     */
    public static Vector v1=new Vector();
    public static Vector v2=new Vector();
    public static Vector v3=new Vector();
    public static Vector v4=new Vector();
    public static Vector v5=new Vector();
    public static Vector v6=new Vector();


    public static int v1Counter=0,v2Counter=0,v3Counter=0,v4Counter=0,v5Counter=0,v6Counter=0;
    
      
    
    public static int Sences(IWord s1,String s2,IDictionary dict, IIndexWord idxWord,int senseNum){
    int sw=0;
    ISynset synset = s1 . getSynset ();
    for( IWord w : synset . getWords ())
    {System .out . print(w. getLemma ()+"  ");
     if (w.getLemma().toString().contains(s2)) sw=1;
     if (w.getLemma().toString()!=null){
                                          v3.add(v3Counter,w.getLemma().toString());
                                          v3Counter++;
                                          if(senseNum<3){
                                          idxWord = dict . getIndexWord (w.getLemma().toString(), POS.VERB);
                                          if(idxWord==null)
                                          idxWord = dict . getIndexWord (w.getLemma().toString(), POS.NOUN);
                                           if(idxWord==null)
                                          idxWord = dict . getIndexWord (w.getLemma().toString(), POS.ADJECTIVE);
                                          for(int i=0;i<idxWord.getWordIDs().size();i++){
                                                        IWordID wordID = idxWord . getWordIDs ().get (i) ;
                                                        IWord word = dict . getWord ( wordID );
                                                          ISynset synset1 = word . getSynset ();
                                                          for( IWord w1 : synset1 . getWords ()){
                                                                            System .out . print(w. getLemma ()+"  ");
                                                                            if (w.getLemma().toString().contains(s2)) sw=1;
                                                                            if (w.getLemma().toString()!=null){
                                                                                     v3.add(v3Counter,w.getLemma().toString());
                                                                                     v3Counter++;
                                                                            }
                                                           }
                                          }
                                          }
     }
    }
    
                                          
    System .out . print("       ");  
    return sw;
  }
  
 //------------------------semantic synset--------------------------------------------------------------   
   public static int Hypernym(IWord s1,String s2,int senseNum) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
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
                 v2.add(v2Counter,word.toString());v2Counter++;
                 if(senseNum<5){
                     
                 //extract second level hypernyms if senses are low
                 String wnhome1 = System . getenv (" WNHOME ");
                 String path1 = wnhome1 + File . separator + " dict ";
                 URL url = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
                 IDictionary dict = new Dictionary ( url);
                 dict . open ();
                 IIndexWord idxWord = dict . getIndexWord (word, POS.VERB);
                 if(idxWord==null)
                          idxWord = dict . getIndexWord (word, POS.NOUN);
                 if(idxWord==null)
                          idxWord = dict . getIndexWord (word, POS.ADJECTIVE);
                 
                 for(int j=0;j<idxWord.getWordIDs().size();j++){
                        IWordID wordID = idxWord . getWordIDs ().get (j) ;
                        IWord word1 = dict . getWord ( wordID );
                        ISynset synset1 = word1 . getSynset ();
                       List < ISynsetID > hypernyms1 =synset1 . getRelatedSynsets ( Pointer . HYPERNYM );
                       List <IWord > words1 ;
                       for( ISynsetID sid1 : hypernyms1 ){
                            words1 = dict1 . getSynset (sid1). getWords ();
                            System .out . print (sid1 + " {");
                           for( Iterator <IWord > i1 = words1. iterator (); i1. hasNext () ;){
                                String word2=i1. next (). getLemma ();
                                System .out . print (word2);
           // if (word.contains(s2)) sw=1;
                                if (word2.toString()!=null)
                                   {
                                     if(word2.toString()=="approach")
                                     {    
                                         System.out.println(" ");
                                     }
                                     
                                     v3.add(v3Counter,word2.toString());v3Counter++;
                                    }
                                if(i1. hasNext ())
                                    System .out . print (", ");
                           }
                       }
                 }
              }
             //end extract second level hypernyms    
            }
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
 //------------------------------------------------------------------------------------- 
   //-------------------lexical---Iword----------------------------------------------------------------   
   public static int Derived(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
   // ISynset synset = s1 . getSynset ();
    List < IWordID > derived =s1.getRelatedWords( Pointer.DERIVATIONALLY_RELATED);
    List <IWord > words ;
    IWord words1 ;
    for( IWordID sid : derived ){
        words1 = dict1 .getWord(sid);
        System .out . println (words1.getLemma());
       // if (words1.toString().contains(s2)) sw=1;
       if (words1.toString()!=null)
            {v2.add(v2Counter,words1.getLemma().toString());v2Counter++;}
    }    
    return sw;
  }
    //-------------------lexical---Iword----------------------------------------------------------------   
   public static int Perteinym(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
   // ISynset synset = s1 . getSynset ();
    List < IWordID > derived =s1.getRelatedWords( Pointer.PERTAINYM);
    List <IWord > words ;
    IWord words1 ;
    for( IWordID sid : derived ){
        words1 = dict1 .getWord(sid);
        System .out . println (words1.getLemma());
        if (words1.toString()!=null)
        {v1.add(v1Counter,words1.getLemma().toString());v1Counter++;}
    }    
    return sw;
  }
 //------------------------semantic synset--------------------------------------------------------------   
   public static int Cause(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer .CAUSE );
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
            //if (word.contains(s2)) sw=1;
            if (word.toString()!=null)
            {v1.add(v1Counter,word.toString());v1Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
 //-------------------------------------------------------------------------------------  
    //------------------------semantic synset--------------------------------------------------------------   
   public static int Entailment(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.ENTAILMENT );
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
           // if (word.contains(s2)) sw=1;
            if (word.toString()!=null)
             {v1.add(v1Counter,word.toString());v1Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
     //------------------------semantic synset--------------------------------------------------------------   
   public static int AlsoSee(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.ALSO_SEE );
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
           // if (word.contains(s2)) sw=1;
               if (word.toString()!=null)
                {v4.add(v4Counter,word.toString());v4Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
       //------------------------semantic synset--------------------------------------------------------------   
   public static int Attribute(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.ATTRIBUTE );
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
           // if (word.contains(s2)) sw=1;
              if (word.toString()!=null)
                {v4.add(v4Counter,word.toString());v4Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
       //------------------------semantic synset--------------------------------------------------------------   
   public static int Domain(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.TOPIC );
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
            //if (word.contains(s2)) sw=1;
              if (word.toString()!=null)
                {v4.add(v4Counter,word.toString());v4Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
       //------------------------semantic synset--------------------------------------------------------------   
   public static int MeronymMem(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.MERONYM_MEMBER);
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
          //  if (word.contains(s2)) sw=1;
           if (word.toString()!=null)
            {v5.add(v5Counter,word.toString());v5Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
//------------------------------------------------------------------------------------- 
   //------------------------semantic synset--------------------------------------------------------------   
   public static int MeronymSub(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.MERONYM_SUBSTANCE);
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
            if (word.contains(s2)) sw=1;
        if (word.toString()!=null)
            {v5.add(v5Counter,word.toString());v5Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
   //------------------------semantic synset--------------------------------------------------------------   
   public static int MeronymPart(IWord s1,String s2) throws IOException{
    int sw=0;
    String wnhome = System . getenv (" WNHOME ");
    String path = wnhome + File . separator + " dict ";
    URL url1 = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
    IDictionary dict1 = new Dictionary ( url1);
    dict1 . open ();
    ISynset synset = s1 . getSynset ();
    List < ISynsetID > syns =synset . getRelatedSynsets ( Pointer.MERONYM_PART);
    List <IWord > words ;
    for( ISynsetID sid : syns ){
        words = dict1 . getSynset (sid). getWords ();
        System .out . print (sid + " {");
        for( Iterator <IWord > i = words . iterator (); i. hasNext () ;){
            String word=i. next (). getLemma ();
            System .out . print (word);
            if (word.contains(s2)) sw=1;
            if (word.toString()!=null)
            {v5.add(v5Counter,word.toString());v5Counter++;}
           if(i. hasNext ())
           System .out . print (", ");
        }
    }    
    return sw;
  }
   //-----------------------------------------------------
  /*
   public List<ISynsetID> getListHypernym(ISynsetID sid_pa) throws IOException {
    IDictionary dict = openDictionary();
    dict.open(); //Open the dictionary to start looking for LEMMA
    List<ISynsetID> hypernym_list = new ArrayList<>();

    boolean end = false;

    while (!end) {
        hypernym_list.add(sid_pa);
        List<ISynsetID> hypernym_tmp = dict.getSynset(sid_pa).getRelatedSynsets(Pointer.HYPERNYM);
        if (hypernym_tmp.isEmpty()) {
            end = true;
        } else {
            sid_pa = hypernym_tmp.get(0);//we will stick with the first hypernym
        }

    }

    //for(int i =0; i< hypernym_list.size();i++){
    //    System.out.println(hypernym_list.get(i));
    //}
    return hyp;
}
*/
   //-----------------------------------------------------
  public static void main(String word1) throws MalformedURLException, IOException {
        // TODO code application logic here
       // String word2="sport";
        String word2="";
        double score=0;
        double similar=0;
        double part=0;
        double other=0;
        double hypDer=0;
        double sen=0;
        v1Counter=v2Counter=v3Counter=v4Counter=v5Counter=v6Counter=0;
        String wnhome = System . getenv (" WNHOME ");
        String path = wnhome + File . separator + " dict ";
        URL url = new URL("file", null , "F:\\FFOutput\\Question-answer\\implementation\\myprog\\libSemSimilarity\\Jwnl\\WordNet\\3.0\\dict\\" );
        IDictionary dict = new Dictionary ( url);
        dict . open ();
        IIndexWord idxWord = dict . getIndexWord (word1, POS.VERB);
        if(idxWord==null)
           idxWord = dict . getIndexWord (word1, POS.NOUN);
         if(idxWord==null)
           idxWord = dict . getIndexWord (word1, POS.ADJECTIVE);
           for(int i=0;i<idxWord.getWordIDs().size();i++){
            
                   IWordID wordID = idxWord . getWordIDs ().get (i) ;
                   IWord word = dict . getWord ( wordID );
                   ISynset synset = word . getSynset ();
                 
                  double cause=Cause(word,word2); System.out.println(cause);
                  double entailment=Entailment(word,word2); System.out.println(entailment);
                  double perteinym=Perteinym(word,word2); System.out.println(perteinym);
                 
                  double hypernym=Hypernym(word,word2,idxWord.getWordIDs().size()); System.out.println(hypernym);
                  double derived=Derived(word,word2); System.out.println(derived);
                  
                  double sences=Sences(word,word2,dict,idxWord,idxWord.getWordIDs().size()); System.out.println(sences);
                 
                  double alsoSee=AlsoSee(word,word2); System.out.println(alsoSee);
                  double attribute=Attribute(word,word2); System.out.println(attribute);
                  double domain=Domain(word,word2); System.out.println(domain);
                  
                  double meronymMem=MeronymMem(word,word2); System.out.println(meronymMem);
                  double meronymPart=MeronymPart(word,word2); System.out.println(meronymPart);
                  double meronymSub=MeronymSub(word,word2); System.out.println(meronymSub);
                
                if(cause==1 || perteinym==1 || entailment==1)similar=1;
                if(hypernym==1 || derived==1)hypDer=0.9;
                if(sences==1)sen=0.8;
                if(meronymMem==1||meronymPart==1||meronymSub==1)part=0.6;
                if(alsoSee==1 || attribute==1 ||domain==1)other=0.4;
               
              if(similar>score)score=similar;
              else if(hypDer>score)score=hypernym;
              else if(sences>score)score=sen;
              else if(meronymMem>score)score=meronymMem;
        }
  }
 
}
