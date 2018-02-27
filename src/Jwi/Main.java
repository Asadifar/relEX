/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jwi;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author WIN8
 */
public class Main {
  public static String main(String str) throws FileNotFoundException, IOException{
  String cla=DBpediaClassFinding.main(str);
  System.out.println(cla);
  return cla;
  }  
}
