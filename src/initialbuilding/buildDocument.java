/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
*/
package initialbuilding;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.tartarus.snowball.ext.EnglishStemmer;

import java.util.List;


public class buildDocument {
    public static Set<String> stopWords = new LinkedHashSet<String>();    
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
      
    }

  
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
    public static String Stemmer(String line){

Properties properties = new Properties();
    properties.put("annotators","tokenize,ssplit,pos,lemma");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
    if(!line.isEmpty())
    {//String[] words = line.split(" ");
       // line="";
    
    //  for(int i = 0; i < words.length; i++){
        Annotation annotation = new Annotation (line);
        pipeline.annotate(annotation);
        String tokenLemma="";
        for (CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(TreeAnnotation.class);
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                tokenLemma = token.get(LemmaAnnotation.class);
            }
        }
        line=tokenLemma;
  //  }
    
    }
      return line;
    }
 }
