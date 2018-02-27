/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jwi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Vector;







public class ReadFromDBPediaClassesFile {
  
     public static double PropertyAnalysis(String GProperty,String QProperty){
 //--------split Property to it s constituent
                       double score=0;                       
                       String property=GProperty;
                        property=removeAdded(property);
                        String labelPropremoved=property;
                        if(GProperty.contentEquals(QProperty)){
                            return 1;
                        }
                        String[] PropInfo=property.split(" ");
                        int counterProperty=0;
                        double sumScore=0;
                        //-------------------------------
                        double zarib=1.0/PropInfo.length;
                        if(PropInfo.length>=2)
                        for(int f=0;f<PropInfo.length;f++){
                            property=PropInfo[counterProperty];
                             property=initialbuilding.buildDocument.Stemmer(property);
                           // property=initialbuilding.buildDocument.removeFirstSpace(property);
                             counterProperty++;
                            
                            score=similarityCalculation(QProperty,property);
                           if (score>=0.8)
                            sumScore=((zarib+0.2)*score)+sumScore;
                           else
                            sumScore=score*zarib+sumScore;
                        }
                       labelPropremoved=initialbuilding.buildDocument.Stemmer(labelPropremoved);
                        score=similarityCalculation(QProperty,labelPropremoved);  
                        if (score<sumScore)
                           score=sumScore;
return score;
     }
     public static double DomainAnalysis(String GDomain,String QDomain){
     double score=0;
     String[] Temp;
     Temp=QDomain.split("  ");
     int sw=0;
     int steps=0;
     for(int i=0;i<Temp.length-1;i++)
     {
       if (Temp[i].contentEquals(GDomain))
       { sw=1;break;}
       else
           steps++;
                   
     }
     if(sw==1)
         score=1-(0.1*steps);
      
     return score;
     }
         
      public static double RangeAnalysis(String GRange,String QRange){
     double score=0;int sw=0;
     String[] Temp;
     if(
             (GRange.contentEquals("xsd:non negative integer") ||GRange.contentEquals("xsd: integer")|| GRange.contentEquals("xsd: double"))
             &&(QRange.contentEquals("Number")))
             {
             sw=1;return 1;
             }
     else if(
             (GRange.contentEquals("xsd:date") ||GRange.contentEquals("xsd:g year"))
             &&(QRange.contentEquals("Date")))
             {
             sw=1;return 1;
             }
     else if(
             (GRange.contentEquals("xsd:string") ||GRange.contentEquals("owl:thing"))
             )
             {
             sw=1;return 1;
             }
     Temp=QRange.split("  ");
     
     int steps=0;
     
     for(int i=0;i<Temp.length-1;i++)
     {
       if (Temp[i].contentEquals(GRange))
            { sw=1;break;}
       else
           steps++;
       
                   
     }
     if(sw==1)
         score=1-(0.1*steps);
      
     return score;
     }
        
     
//-------------------------replace capitals by lowercases
    public static String replaceCapitals(String labelProp){
  String[] PropInfo;   
  Character ch1='a';
  int len=labelProp.length();
  String Str=labelProp;
                                for(int l= 0; l < len; l++) {
                                      Character ch = Str.charAt(l);
                                     
                                      if(l<len-1)
                                      {ch1 = Str.charAt(l+1);}
                                      
                                      if(Character.isUpperCase(ch) && Character.isLowerCase(ch1) ){
                                         PropInfo=Str.split(ch.toString());
                                         
                                         if(l!=0)
                                            
                                             Str=PropInfo[0]+" ";
                                         else
                                             Str="";
                                         
                                         int j=1;
                                         while(j<PropInfo.length){
                                             Str=Str+ch.toString().toLowerCase()+PropInfo[j];j++;}
                                         len=Str.length();
                                      }
                                 
                                          
                                }     
    return Str;
    }
                        //----------------------------------------------------------


//-------------------------
    public static String removeFirstSpace(String word){
    String res="";
             //   if(word=="show"){
               //     System.out.println(word);
             //   }
                
                if(word.charAt(0)==' ' )
                     {
                            for(int i=1;i<word.length();i++)
                                res=res+word.charAt(i);
                            word=res;
                     } 
    
        return word;
    }
    
     public static String removeAdded(String property){
    String[] res;
             //   if(word=="show"){
               //     System.out.println(word);
             //   }
                if(property.contains(" in "))
                { res=property.split(" in ");
                   property=res[0]+" "+res[1];
                }
                if(property.contains(" by"))
                { res=property.split(" by");
                   property=res[0];
                }
                if(property.contains(" of "))
                { res=property.split(" of ");
                   property=res[0]+" "+res[1];
                }
                if(property.contains(" date"))
                { res=property.split(" date");
                   property=res[0];
                }
                 if(property.contains(" place"))
                { res=property.split(" place");
                   property=res[0];
                }
                  if(property.contains(" name"))
                { res=property.split(" name");
                   property=res[0];
                }
                   if(property.contains(" type"))
                { res=property.split(" type");
                   property=res[0];
                }
                    
        return property;
    }
    
     
                            
    
  private static int Minimum (int a, int b, int c) {
  int mi;

    mi = a;
    if (b < mi) {
      mi = b;
    }
    if (c < mi) {
      mi = c;
    }
    return mi;

  }
  
        //..............................similarity score...........
                                     
      public static double similarityCalculation(String word,String property){
          double score=0;    
          double wuPalmer=0;
          int i;     
                    if(word.contentEquals(property))
                          return 1;
                    else{
                      for(i=0;i<JWI.v1Counter;i++){
                      if (JWI.v1.get(i).toString().contentEquals(property)/*||
                                  JWI.v1.get(i).toString().endsWith(" "+property)||
                                  JWI.v1.get(i).toString().endsWith("_"+property)||
                                  JWI.v1.get(i).toString().contains(property+" ")||
                                  JWI.v1.get(i).toString().contains("_"+property+" ")||
                                  JWI.v1.get(i).toString().contains(property+"_")*/)
                         {score=0.9;return score;}
                      }//end  for(i=0;i<JWI.v1Counter;i++){
                      if(i==JWI.v1Counter)
                          for(i=0;i<JWI.v2Counter;i++){
                          if (JWI.v2.get(i).toString().contentEquals(property)/*||
                                  JWI.v2.get(i).toString().endsWith(" "+property)||
                                  JWI.v2.get(i).toString().endsWith("_"+property)||
                                  JWI.v2.get(i).toString().contains(property+" ")||
                                  JWI.v2.get(i).toString().contains("_"+property+" ")||
                                  JWI.v2.get(i).toString().contains(property+"_")*/)
                          {
                              score=0.7;return score;}
                          }//end for(i=0;i<JWI.v2Counter;i++){
                      if(i==JWI.v2Counter)
                          for(i=0;i<JWI.v3Counter;i++){
                          if (JWI.v3.get(i).toString().contentEquals(property)/*||
                                  JWI.v3.get(i).toString().endsWith(" "+property)||
                                  JWI.v3.get(i).toString().endsWith("_"+property)||
                                  JWI.v3.get(i).toString().contains(property+" ")||
                                  JWI.v3.get(i).toString().contains("_"+property+" ")||
                                  JWI.v3.get(i).toString().contains(property+"_")*/)
                          { score=0.4;return score;}
                          }
                      if(i==JWI.v3Counter)
                          for(i=0;i<JWI.v4Counter;i++){
                          if (JWI.v4.get(i).toString().contentEquals(property)/*||
                                  JWI.v4.get(i).toString().endsWith(" "+property)||
                                  JWI.v4.get(i).toString().endsWith("_"+property)||
                                  JWI.v4.get(i).toString().contains(property+" ")||
                                  JWI.v4.get(i).toString().contains("_"+property+" ")||
                                  JWI.v4.get(i).toString().contains(property+"_")*/)
                          {score=0.3;return score;}
                     if(i==JWI.v4Counter)
                      for(i=0;i<JWI.v5Counter;i++){
                      if (JWI.v5.get(i).toString().contentEquals(property)/*||
                                 JWI.v5.get(i).toString().endsWith(" "+property)||
                                  JWI.v5.get(i).toString().endsWith("_"+property)||
                                  JWI.v5.get(i).toString().contains(property+" ")||
                                  JWI.v5.get(i).toString().contains("_"+property+" ")||
                                  JWI.v5.get(i).toString().contains(property+"_")*/)
                      {score=0.2;return score;}
                      }
                      }
                        //pathScore=SimilarityCalculation.main(word,property);
                       // neighborScore=JWI.main(word,property);
                        //if(neighborScore>pathScore)
                          // score=neighborScore;
                        //else score=pathScore;
                       /* if(score<0.4)
                          wuPalmer=SimilarityCalculation.main(word,property)*1.0;
                        if (score<wuPalmer)score=wuPalmer;*/
       }
                    return score;

      }                  
  
  
  
//-------------Jcard-----------------------------
 /*
  public static int LD (String s1, String s2) {
  KShingling ks = new KShingling(k);
        int[] profile1 = ks.getArrayProfile(s1);
        int[] profile2 = ks.getArrayProfile(s2);
    
        int length = Math.max(profile1.length, profile2.length);
        profile1 = java.util.Arrays.copyOf(profile1, length);
        profile2 = java.util.Arrays.copyOf(profile2, length);
        
        int inter = 0;
        int union = 0;
        
        for (int i = 0; i < length; i++) {
            if (profile1[i] > 0 || profile2[i] > 0) {
                union++;
                
                if (profile1[i] > 0 && profile2[i] > 0) {
                    inter++;
                }
            }
        }
    
        return (double) inter / union;
  
}*/
  //*****************************
  // Compute Levenshtein distance
  //*****************************

  public static int LD (String s, String t) {
  int d[][]; // matrix
  int n; // length of s
  int m; // length of t
  int i; // iterates through s
  int j; // iterates through t
  char s_i; // ith character of s
  char t_j; // jth character of t
  int cost; // cost

    // Step 1

    n = s.length ();
    m = t.length ();
    if (n == 0) {
      return m;
    }
    if (m == 0) {
      return n;
    }
    d = new int[n+1][m+1];

    // Step 2

    for (i = 0; i <= n; i++) {
      d[i][0] = i;
    }

    for (j = 0; j <= m; j++) {
      d[0][j] = j;
    }

    // Step 3

    for (i = 1; i <= n; i++) {

      s_i = s.charAt (i - 1);

      // Step 4

      for (j = 1; j <= m; j++) {

        t_j = t.charAt (j - 1);

        // Step 5

        if (s_i == t_j) {
          cost = 0;
        }
        else {
          cost = 1;
        }

        // Step 6

        d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);

      }

    }

    // Step 7

    return d[n][m];

  }


//----------------
         public static int levenshtein(String s1,String t)
		{
		     if (s1 == null || t == null) {
          throw new IllegalArgumentException("Strings must not be null");
      }

        int n = s1.length(); // length of s
      int m = t.length(); // length of t

     /* if (n == 0) {
          return m;
      } else if (m == 0) {
          return n;
      }
*/
      if (n > m) {
          // swap the input strings to consume less memory
          String tmp = s1;
          s1 = t;
          t = tmp;
          n = m;
          m = t.length();
      }

      int p[] = new int[n+1]; //'previous' cost array, horizontally
      int d[] = new int[n+1]; // cost array, horizontally
      int _d[]; //placeholder to assist in swapping p and d

      // indexes into strings s and t
      int i; // iterates through s
      int j; // iterates through t

      char t_j; // jth character of t

      int cost; // cost

      for (i = 0; i<=n; i++) {
          p[i] = i;
      }

      for (j = 1; j<=m; j++) {
          t_j = t.charAt(j-1);
          d[0] = j;

          for (i=1; i<=n; i++) {
              cost = s1.charAt(i-1)==t_j ? 0 : 1;
              // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
              d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
          }

          // copy current distance counts to 'previous row' distance counts
          _d = p;
          p = d;
          d = _d;
      }

      // our last action in the above loop was to switch d and p, so p now 
      // actually has the most recent cost counts
      return p[n];
    		 
		}
    //-----------------

    //----------------------------------------------------------------------------     
 public static void main(String[] args) throws FileNotFoundException, IOException {
   
     //String service = "http://dbpedia.org/sparql";
     double MaxStrSimValue=0;
     String MaxStrSimUri="";
     String MaxSemSimUri="";
     
     double MaxSemSimValue=0;
     String MaxPropStrSim="";
     String MaxPropSemSim="";
     String MaxNonLemmatize="";
     String MaxSemFullProperty="";
     String MaxSemFullUri="";
     String MaxPropSemSim1="";
      Vector v=new Vector();
     String lineInputFile2="";   
     String[] PropInfo=new String[50];
    String Trnsactions="";
    String mainProp="";
    String GProperty="";
    String GDomain="";
    String GRange="";
    double zarib=0.0;
    // Connection cn = null;
    // DBClass ins;
   //  ins = new DBClass();
    // cn=DBClass.getConnection(Trnsactions);  
     
     int counterVector=0;
     
    
    
 String InputFile1 = "F:\\FFOutput\\Question-answer\\implementation\\MyActionsInHQA\\2-RDFData\\2-SemanticSimilaritySubQuestionWithSchema\\INOut\\Input.txt";

        // This will reference one line at a time
 String word ;//= "win";
 String line="";
 String QDomain="";
 String QProperty="";
 String QRange="";


try {   
        FileReader fileReader = new FileReader(InputFile1);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null) {
            
            MaxStrSimUri="";
              MaxSemSimUri="";
              MaxSemSimValue=0;
              double neighborScore=0;
              double pathScore=0;
              double score=0;
              double wuPalmer=0;
              double MaxscorePredicate=0;
              double MaxscoreDomain=0;
              double MaxscoreRange=0;
              String MaxProp="";String MaxDomain="";String MaxRange="";
              MaxPropStrSim="";
              MaxPropSemSim="";
              MaxNonLemmatize="";
              MaxSemFullProperty="";
              MaxSemFullUri="";
              MaxPropSemSim1="";               
              String[] totalLine;
              totalLine=line.split("  ");
              
              QDomain=totalLine[0];
              QProperty=totalLine[1];
              QRange=totalLine[2];
              //word=removeFirstSpace(word);
             // word=removeFirstSpace(word);
              JWI.main(QProperty);
              String QDomainClass=DBpediaClassFinding.main(QDomain);
              String QRangeClass=DBpediaClassFinding.main(QRange);
              counterVector=0;                 
              //-----------------read from DBPedia Classes File-------------
               String InputFile2 = "F:\\FFOutput\\Question-answer\\implementation\\myprog\\QueryLib\\INOut\\DPR.txt";

              try {   
                    FileReader fileReader2 = new FileReader(InputFile2);
                    BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
                    while((lineInputFile2 = bufferedReader2.readLine()) != null) {
                      if(lineInputFile2.contains("influenced"))
                        {
                           System.out.println();
                        }
                        int tf;
                        
                        PropInfo=lineInputFile2.split("  ");
                        mainProp=PropInfo[0];
                        GProperty=PropInfo[1];
                       
                        GDomain=PropInfo[2];
                        GRange=PropInfo[3];
                       //------------------------------
                        
                        GProperty=replaceCapitals(GProperty);
                        GDomain=replaceCapitals(GDomain);
                        GRange=replaceCapitals(GRange);
                        QDomainClass=replaceCapitals(QDomainClass);
                        QRangeClass=replaceCapitals(QRangeClass);

                        //-----------------------------------
                        double scorePredicate=PropertyAnalysis(GProperty,QProperty);
                        //scorePredicate=PropertyAnalysis("band member","member");
                        double scoreDomain=DomainAnalysis(GDomain,QDomainClass);
                        double scoreRange=RangeAnalysis(GRange,QRangeClass);
                        double scoreDomainInverse=DomainAnalysis(GDomain,QRangeClass);
                        double scoreRangeInverse=RangeAnalysis(GRange,QDomainClass);
                        
                        if(scorePredicate>0.3)
                        {
                      v.add(counterVector,Double.toString(scoreDomain));counterVector++;
                       v.add(counterVector,Double.toString(scorePredicate));counterVector++;
                       v.add(counterVector,Double.toString(scoreRange));counterVector++;
                       v.add(counterVector,GDomain);counterVector++;
                       v.add(counterVector,GProperty);counterVector++;
                       v.add(counterVector,GRange);counterVector++;
                        }
                        if( scoreDomain>=0.5 && scoreRange>=0.5)
                        {
                       v.add(counterVector,Double.toString(scoreDomain));counterVector++;
                       v.add(counterVector,Double.toString(scorePredicate));counterVector++;
                       v.add(counterVector,Double.toString(scoreRange));counterVector++;
                       v.add(counterVector,GDomain);counterVector++;
                       v.add(counterVector,GProperty);counterVector++;
                       v.add(counterVector,GRange);counterVector++;
                        }
                        
                        if( scoreDomainInverse>=0.5 && scoreRangeInverse>=0.5)
                        {
                       v.add(counterVector,Double.toString(scoreDomain));counterVector++;
                       v.add(counterVector,Double.toString(scorePredicate));counterVector++;
                       v.add(counterVector,Double.toString(scoreRange));counterVector++;
                       v.add(counterVector,GRange);counterVector++;
                       v.add(counterVector,GProperty);counterVector++;
                       v.add(counterVector,GDomain);counterVector++;
                        }
                        if(scorePredicate>=MaxscorePredicate && scoreDomain>=MaxscoreDomain && scoreRange>=MaxscoreRange)
                               {   MaxscorePredicate=scorePredicate;
                                   MaxscoreDomain=scoreDomain;
                                   MaxscoreRange=scoreRange;
                                   
                                   MaxProp=GProperty; 
                                   MaxDomain=GDomain;
                                   MaxRange=GRange;                                           
                               }
                         if(scorePredicate>=MaxscorePredicate && scoreDomainInverse>=MaxscoreDomain && scoreRangeInverse>=MaxscoreRange)
                               {   MaxscorePredicate=scorePredicate;
                                   MaxscoreDomain=scoreDomain;
                                   MaxscoreRange=scoreRange;
                                   
                                   MaxProp=GProperty; 
                                   MaxDomain=GRange;
                                   MaxRange=GDomain;                                           
                               }
                       // if(scorePredicate>=0.4)
                         //    { 
                           //     v.add(counterVector,GProperty);counterVector++;} 
                   }   //End read lines from text     
                   bufferedReader2.close();      
                 }  
                 catch(FileNotFoundException ex) {
                 System.out.println("Unable to open file '" +InputFile2 + "'");                
                 }
                 catch(IOException ex) {
                 System.out.println("Error reading file '"+ InputFile2 + "'");                  
                 }
            //*********************************write in file************************************
                        String output = "F:\\FFOutput\\Question-answer\\implementation\\MyActionsInHQA\\2-RDFData\\2-SemanticSimilaritySubQuestionWithSchema\\INOut\\Output.txt";
                        try {
                             FileWriter fileWriter =new FileWriter(output,true);
                             BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
                             bufferedWriter.write(line);
                             bufferedWriter.write("   ->     ");
                             
                             bufferedWriter.write(MaxDomain);
                             bufferedWriter.write("  ");
                             bufferedWriter.write(MaxProp);
                             bufferedWriter.write("  " );
                             bufferedWriter.write(MaxRange);
                             bufferedWriter.write("  ");
                             bufferedWriter.write("   ->     ");
                             
                              bufferedWriter.write(Double.toString(MaxscoreDomain));
                             bufferedWriter.write("  ");
                             bufferedWriter.write(Double.toString(MaxscorePredicate));
                             bufferedWriter.write("  " );
                            
                             bufferedWriter.write(Double.toString(MaxscoreRange));
                             bufferedWriter.write("  ");
                             
                             
                             
                             bufferedWriter.newLine();
                            int k=0;
                             for(int i=0;i<counterVector;i++){
                                 
                                bufferedWriter.write((String) v.get(i));
                                 bufferedWriter.write("  ");
                             if(k==5)
                                 { k=0;bufferedWriter.newLine();}
                                 else
                                 k++;
                             }
                             bufferedWriter.newLine();
                              bufferedWriter.write("--------------------------------------------------------------------------  ");
                             bufferedWriter.newLine();
                           bufferedWriter.close();
                          }//end try write
                        catch(IOException ex) {
                             System.out.println("Error writing to file '"+ output + "'");
                        }//end write in file  
                         //************************************************************
            ///-------------------------end print result-----------------------------------------------------
        
        }   //End read lines from text     
          bufferedReader.close();      
 }  
 catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" +InputFile1 + "'");                
        }
 catch(IOException ex) {
            System.out.println("Error reading file '"+ InputFile1 + "'");                  
        }
    
 //-------------------------------end read InputFile1----------------------------           
            
}//main
}//class
   
   