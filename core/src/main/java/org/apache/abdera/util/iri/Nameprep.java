/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  The ASF licenses this file to You
* under the Apache License, Version 2.0 (the "License"); you may not
* use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.  For additional information regarding
* copyright in this work, please see the NOTICE file in the top level
* directory of this distribution.
*/
package org.apache.abdera.util.iri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.BitSet;

import org.apache.abdera.util.ChainableBitSet;
import org.apache.abdera.util.io.CodepointIterator;
import org.apache.abdera.util.unicode.Normalizer;


/**
 * Implements the Nameprep protocol
 */
public class Nameprep {

  public static String prep(String s, boolean allowunassigned) {
    NameprepCodepointIterator r = null;
    try {
      StringBuffer buf = new StringBuffer();
      CodepointIterator ci = CodepointIterator.forCharSequence(s);
      r = new NameprepCodepointIterator(ci,allowunassigned);
      while(r.hasNext()) {
        int i = r.next();
        if (i != -1)
        buf.append((char)i);
      }
      return Normalizer.normalize(buf.toString(),Normalizer.Form.KC).toString();
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
  
  public static String prep(String s) {
    return prep(s,false);
  }
  
  public static final BitSet B1 = new ChainableBitSet()
    .set2('\u00AD','\u034F','\u1806','\u180B','\u180C','\u180D','\u200B',
          '\u200C','\u200D','\u2060','\uFE00','\uFE01','\uFE02','\uFE03',
          '\uFE04','\uFE05','\uFE06','\uFE07','\uFE08','\uFE09','\uFE0A',
          '\uFE0B','\uFE0C','\uFE0D','\uFE0E','\uFE0F','\uFEFF',
          // Not listed in B1, but treated as map-to-nothing by others
          0x8F,0xA0,0x86,0x8b,0x80,0x81,0x88
          );
  
  public static final int[][] B2 = loadb2();
  
  public static final int[] B2(int c) {   
    return (B2[c] != null) ? B2[c] : null;
  }
  
  private static int[] parse(String rep) {
    String[] tokens = rep.trim().split(" ");
    int[] i = new int[tokens.length];
    for (int n = 0; n < tokens.length; n++) {
      i[n]=Integer.parseInt(tokens[n],16);
    }
    return i;
  }
  
  private static final String B2DAT = "/org/apache/abdera/util/iri/data/B2.dat";
  
  public static int[][] loadb2() {
    int[][] map = new int[120764][];
    try {
      InputStream in = Nameprep.class.getResourceAsStream(B2DAT);
      InputStreamReader rdr = new InputStreamReader(in);
      BufferedReader bufr = new BufferedReader(rdr);
      String line = null;
      while((line = bufr.readLine()) != null) {
        String[] tokens = line.trim().split("\\s*;\\s*");
        int n = Integer.parseInt(tokens[0],16);
        int[] i = parse(tokens[1]);
        map[n] = i;
      }
    } catch (IOException e) {
    }
    return map;
  }
  
  public static final BitSet UNASSIGNED = new ChainableBitSet()  
  .set2('\u0221')
  .set2('\u0234','\u024F')
  .set2('\u02AE','\u02AF')
  .set2('\u02EF','\u02FF')
  .set2('\u0350','\u035F')
  .set2('\u0370','\u0373')
  .set2('\u0376','\u0379')
  .set2('\u037B','\u037D')
  .set2('\u037F','\u0383')
  .set2('\u038B')
  .set2('\u038D')
  .set2('\u03A2')
  .set2('\u03CF')
  .set2('\u03F7','\u03FF')
  .set2('\u0487')
  .set2('\u04CF')
  .set2('\u04F6','\u04F7')
  .set2('\u04FA','\u04FF')
  .set2('\u0510','\u0530')
  .set2('\u0557','\u0558')
  .set2('\u0560')
  .set2('\u0588')
  .set2('\u058B','\u0590')
  .set2('\u05A2')
  .set2('\u05BA')
  .set2('\u05C5','\u05CF')
  .set2('\u05EB','\u05EF')
  .set2('\u05F5','\u060B')
  .set2('\u060D','\u061A')
  .set2('\u061C','\u061E')
  .set2('\u0620')
  .set2('\u063B','\u063F')
  .set2('\u0656','\u065F')
  .set2('\u06EE','\u06EF')
  .set2('\u06FF')
  .set2('\u070E')
  .set2('\u072D','\u072F')
  .set2('\u074B','\u077F')
  .set2('\u07B2','\u0900')
  .set2('\u0904')
  .set2('\u093A','\u093B')
  .set2('\u094E','\u094F')
  .set2('\u0955','\u0957')
  .set2('\u0971','\u0980')
  .set2('\u0984')
  .set2('\u098D','\u098E')
  .set2('\u0991','\u0992')
  .set2('\u09A9')
  .set2('\u09B1')
  .set2('\u09B3','\u09B5')
  .set2('\u09BA','\u09BB')
  .set2('\u09BD')
  .set2('\u09C5','\u09C6')
  .set2('\u09C9','\u09CA')
  .set2('\u09CE','\u09D6')
  .set2('\u09D8','\u09DB')
  .set2('\u09DE')
  .set2('\u09E4','\u09E5')
  .set2('\u09FB','\u0A01')
  .set2('\u0A03','\u0A04')
  .set2('\u0A0B','\u0A0E')
  .set2('\u0A11','\u0A12')
  .set2('\u0A29')
  .set2('\u0A31')
  .set2('\u0A34')
  .set2('\u0A37')
  .set2('\u0A3A','\u0A3B')
  .set2('\u0A3D')
  .set2('\u0A43','\u0A46')
  .set2('\u0A49','\u0A4A')
  .set2('\u0A4E','\u0A58')
  .set2('\u0A5D')
  .set2('\u0A5F','\u0A65')
  .set2('\u0A75','\u0A80')
  .set2('\u0A84')
  .set2('\u0A8C')
  .set2('\u0A8E')
  .set2('\u0A92')
  .set2('\u0AA9')
  .set2('\u0AB1')
  .set2('\u0AB4')
  .set2('\u0ABA','\u0ABB')
  .set2('\u0AC6')
  .set2('\u0ACA')
  .set2('\u0ACE','\u0ACF')
  .set2('\u0AD1','\u0ADF')
  .set2('\u0AE1','\u0AE5')
  .set2('\u0AF0','\u0B00')
  .set2('\u0B04')
  .set2('\u0B0D','\u0B0E')
  .set2('\u0B11','\u0B12')
  .set2('\u0B29')
  .set2('\u0B31')
  .set2('\u0B34','\u0B35')
  .set2('\u0B3A','\u0B3B')
  .set2('\u0B44','\u0B46')
  .set2('\u0B49','\u0B4A')
  .set2('\u0B4E','\u0B55')
  .set2('\u0B58','\u0B5B')
  .set2('\u0B5E')
  .set2('\u0B62','\u0B65')
  .set2('\u0B71','\u0B81')
  .set2('\u0B84')
  .set2('\u0B8B','\u0B8D')
  .set2('\u0B91')
  .set2('\u0B96','\u0B98')
  .set2('\u0B9B')
  .set2('\u0B9D')
  .set2('\u0BA0','\u0BA2')
  .set2('\u0BA5','\u0BA7')
  .set2('\u0BAB','\u0BAD')
  .set2('\u0BB6')
  .set2('\u0BBA','\u0BBD')
  .set2('\u0BC3','\u0BC5')
  .set2('\u0BC9')
  .set2('\u0BCE','\u0BD6')
  .set2('\u0BD8','\u0BE6')
  .set2('\u0BF3','\u0C00')
  .set2('\u0C04')
  .set2('\u0C0D')
  .set2('\u0C11')
  .set2('\u0C29')
  .set2('\u0C34')
  .set2('\u0C3A','\u0C3D')
  .set2('\u0C45')
  .set2('\u0C49')
  .set2('\u0C4E','\u0C54')
  .set2('\u0C57','\u0C5F')
  .set2('\u0C62','\u0C65')
  .set2('\u0C70','\u0C81')
  .set2('\u0C84')
  .set2('\u0C8D')
  .set2('\u0C91')
  .set2('\u0CA9')
  .set2('\u0CB4')
  .set2('\u0CBA','\u0CBD')
  .set2('\u0CC5')
  .set2('\u0CC9')
  .set2('\u0CCE','\u0CD4')
  .set2('\u0CD7','\u0CDD')
  .set2('\u0CDF')
  .set2('\u0CE2','\u0CE5')
  .set2('\u0CF0','\u0D01')
  .set2('\u0D04')
  .set2('\u0D0D')
  .set2('\u0D11')
  .set2('\u0D29')
  .set2('\u0D3A','\u0D3D')
  .set2('\u0D44','\u0D45')
  .set2('\u0D49')
  .set2('\u0D4E','\u0D56')
  .set2('\u0D58','\u0D5F')
  .set2('\u0D62','\u0D65')
  .set2('\u0D70','\u0D81')
  .set2('\u0D84')
  .set2('\u0D97','\u0D99')
  .set2('\u0DB2')
  .set2('\u0DBC')
  .set2('\u0DBE','\u0DBF')
  .set2('\u0DC7','\u0DC9')
  .set2('\u0DCB','\u0DCE')
  .set2('\u0DD5')
  .set2('\u0DD7')
  .set2('\u0DE0','\u0DF1')
  .set2('\u0DF5','\u0E00')
  .set2('\u0E3B','\u0E3E')
  .set2('\u0E5C','\u0E80')
  .set2('\u0E83')
  .set2('\u0E85','\u0E86')
  .set2('\u0E89')
  .set2('\u0E8B','\u0E8C')
  .set2('\u0E8E','\u0E93')
  .set2('\u0E98')
  .set2('\u0EA0')
  .set2('\u0EA4')
  .set2('\u0EA6')
  .set2('\u0EA8','\u0EA9')
  .set2('\u0EAC')
  .set2('\u0EBA')
  .set2('\u0EBE','\u0EBF')
  .set2('\u0EC5')
  .set2('\u0EC7')
  .set2('\u0ECE','\u0ECF')
  .set2('\u0EDA','\u0EDB')
  .set2('\u0EDE','\u0EFF')
  .set2('\u0F48')
  .set2('\u0F6B','\u0F70')
  .set2('\u0F8C','\u0F8F')
  .set2('\u0F98')
  .set2('\u0FBD')
  .set2('\u0FCD','\u0FCE')
  .set2('\u0FD0','\u0FFF')
  .set2('\u1022')
  .set2('\u1028')
  .set2('\u102B')
  .set2('\u1033','\u1035')
  .set2('\u103A','\u103F')
  .set2('\u105A','\u109F')
  .set2('\u10C6','\u10CF')
  .set2('\u10F9','\u10FA')
  .set2('\u10FC','\u10FF')
  .set2('\u115A','\u115E')
  .set2('\u11A3','\u11A7')
  .set2('\u11FA','\u11FF')
  .set2('\u1207')
  .set2('\u1247')
  .set2('\u1249')
  .set2('\u124E','\u124F')
  .set2('\u1257')
  .set2('\u1259')
  .set2('\u125E','\u125F')
  .set2('\u1287')
  .set2('\u1289')
  .set2('\u128E','\u128F')
  .set2('\u12AF')
  .set2('\u12B1')
  .set2('\u12B6','\u12B7')
  .set2('\u12BF')
  .set2('\u12C1')
  .set2('\u12C6','\u12C7')
  .set2('\u12CF')
  .set2('\u12D7')
  .set2('\u12EF')
  .set2('\u130F')
  .set2('\u1311')
  .set2('\u1316','\u1317')
  .set2('\u131F')
  .set2('\u1347')
  .set2('\u135B','\u1360')
  .set2('\u137D','\u139F')
  .set2('\u13F5','\u1400')
  .set2('\u1677','\u167F')
  .set2('\u169D','\u169F')
  .set2('\u16F1','\u16FF')
  .set2('\u170D')
  .set2('\u1715','\u171F')
  .set2('\u1737','\u173F')
  .set2('\u1754','\u175F')
  .set2('\u176D')
  .set2('\u1771')
  .set2('\u1774','\u177F')
  .set2('\u17DD','\u17DF')
  .set2('\u17EA','\u17FF')
  .set2('\u180F')
  .set2('\u181A','\u181F')
  .set2('\u1878','\u187F')
  .set2('\u18AA','\u1DFF')
  .set2('\u1E9C','\u1E9F')
  .set2('\u1EFA','\u1EFF')
  .set2('\u1F16','\u1F17')
  .set2('\u1F1E','\u1F1F')
  .set2('\u1F46','\u1F47')
  .set2('\u1F4E','\u1F4F')
  .set2('\u1F58')
  .set2('\u1F5A')
  .set2('\u1F5C')
  .set2('\u1F5E')
  .set2('\u1F7E','\u1F7F')
  .set2('\u1FB5')
  .set2('\u1FC5')
  .set2('\u1FD4','\u1FD5')
  .set2('\u1FDC')
  .set2('\u1FF0','\u1FF1')
  .set2('\u1FF5')
  .set2('\u1FFF')
  .set2('\u2053','\u2056')
  .set2('\u2058','\u205E')
  .set2('\u2064','\u2069')
  .set2('\u2072','\u2073')
  .set2('\u208F','\u209F')
  .set2('\u20B2','\u20CF')
  .set2('\u20EB','\u20FF')
  .set2('\u213B','\u213C')
  .set2('\u214C','\u2152')
  .set2('\u2184','\u218F')
  .set2('\u23CF','\u23FF')
  .set2('\u2427','\u243F')
  .set2('\u244B','\u245F')
  .set2('\u24FF')
  .set2('\u2614','\u2615')
  .set2('\u2618')
  .set2('\u267E','\u267F')
  .set2('\u268A','\u2700')
  .set2('\u2705')
  .set2('\u270A','\u270B')
  .set2('\u2728')
  .set2('\u274C')
  .set2('\u274E')
  .set2('\u2753','\u2755')
  .set2('\u2757')
  .set2('\u275F','\u2760')
  .set2('\u2795','\u2797')
  .set2('\u27B0')
  .set2('\u27BF','\u27CF')
  .set2('\u27EC','\u27EF')
  .set2('\u2B00','\u2E7F')
  .set2('\u2E9A')
  .set2('\u2EF4','\u2EFF')
  .set2('\u2FD6','\u2FEF')
  .set2('\u2FFC','\u2FFF')
  .set2('\u3040')
  .set2('\u3097','\u3098')
  .set2('\u3100','\u3104')
  .set2('\u312D','\u3130')
  .set2('\u318F')
  .set2('\u31B8','\u31EF')
  .set2('\u321D','\u321F')
  .set2('\u3244','\u3250')
  .set2('\u327C','\u327E')
  .set2('\u32CC','\u32CF')
  .set2('\u32FF')
  .set2('\u3377','\u337A')
  .set2('\u33DE','\u33DF')
  .set2('\u33FF')
  .set2('\u4DB6','\u4DFF')
  .set2('\u9FA6','\u9FFF')
  .set2('\uA48D','\uA48F')
  .set2('\uA4C7','\uABFF')
  .set2('\uD7A4','\uD7FF')
  .set2('\uFA2E','\uFA2F')
  .set2('\uFA6B','\uFAFF')
  .set2('\uFB07','\uFB12')
  .set2('\uFB18','\uFB1C')
  .set2('\uFB37')
  .set2('\uFB3D')
  .set2('\uFB3F')
  .set2('\uFB42')
  .set2('\uFB45')
  .set2('\uFBB2','\uFBD2')
  .set2('\uFD40','\uFD4F')
  .set2('\uFD90','\uFD91')
  .set2('\uFDC8','\uFDCF')
  .set2('\uFDFD','\uFDFF')
  .set2('\uFE10','\uFE1F')
  .set2('\uFE24','\uFE2F')
  .set2('\uFE47','\uFE48')
  .set2('\uFE53')
  .set2('\uFE67')
  .set2('\uFE6C','\uFE6F')
  .set2('\uFE75')
  .set2('\uFEFD','\uFEFE')
  .set2('\uFF00')
  .set2('\uFFBF','\uFFC1')
  .set2('\uFFC8','\uFFC9')
  .set2('\uFFD0','\uFFD1')
  .set2('\uFFD8','\uFFD9')
  .set2('\uFFDD','\uFFDF')
  .set2('\uFFE7')
  .set2('\uFFEF','\uFFF8')
  .set2(0x10000,0x102FF)
  .set2(0x1031F)
  .set2(0x10324,0x1032F)
  .set2(0x1034B,0x103FF)
  .set2(0x10426,0x10427)
  .set2(0x1044E,0x1CFFF)
  .set2(0x1D0F6,0x1D0FF)
  .set2(0x1D127,0x1D129)
  .set2(0x1D1DE,0x1D3FF)
  .set2(0x1D455)
  .set2(0x1D49D)
  .set2(0x1D4A0,0x1D4A1)
  .set2(0x1D4A3,0x1D4A4)
  .set2(0x1D4A7,0x1D4A8)
  .set2(0x1D4AD)
  .set2(0x1D4BA)
  .set2(0x1D4BC)
  .set2(0x1D4C1)
  .set2(0x1D4C4)
  .set2(0x1D506)
  .set2(0x1D50B,0x1D50C)
  .set2(0x1D515)
  .set2(0x1D51D)
  .set2(0x1D53A)
  .set2(0x1D53F)
  .set2(0x1D545)
  .set2(0x1D547,0x1D549)
  .set2(0x1D551)
  .set2(0x1D6A4,0x1D6A7)
  .set2(0x1D7CA,0x1D7CD)
  .set2(0x1D800,0x1FFFD)
  .set2(0x2A6D7,0x2F7FF)
  .set2(0x2FA1E,0x2FFFD)
  .set2(0x30000,0x3FFFD)
  .set2(0x40000,0x4FFFD)
  .set2(0x50000,0x5FFFD)
  .set2(0x60000,0x6FFFD)
  .set2(0x70000,0x7FFFD)
  .set2(0x80000,0x8FFFD)
  .set2(0x90000,0x9FFFD)
  .set2(0xA0000,0xAFFFD)
  .set2(0xB0000,0xBFFFD)
  .set2(0xC0000,0xCFFFD)
  .set2(0xD0000,0xDFFFD)
  .set2(0xE0000)
  .set2(0xE0002,0xE001F)
  .set2(0xE0080,0xEFFFD);

  
  public static final ChainableBitSet PROHIBITED = new ChainableBitSet()

  // c.1.2
   .set2('\u00A0').set2('\u1680').set2('\u2000','\u200B')
   .set2('\u202F').set2('\u205F').set2('\u3000')

  // c.2.2
   .set2('\u0080','\u009F').set2('\u06DD').set2('\u070F')
   .set2('\u180E').set2('\u200C').set2('\u200D').set2('\u2028')
   .set2('\u2029').set2('\u2060').set2('\u2061').set2('\u2062')
   .set2('\u2063').set2('\u206A','\u206F').set2('\uFEFF')
   .set2('\uFFF9','\uFFFC').set2(0x1D173,0x1D17A)
                                             
  // c.3
   .set2('\uE000','\uF8FF').set2(0xF0000,0xFFFFD).set2(0x100000,0x10FFFD)
                   
  // c.4
   .set2('\uFDD0','\uFDEF').set2('\uFFFE','\uFFFF').set2(0x1FFFE,0x1FFFF)
   .set2(0x2FFFE,0x2FFFF).set2(0x3FFFE,0x3FFFF).set2(0x4FFFE,0x4FFFF)
   .set2(0x5FFFE,0x5FFFF).set2(0x6FFFE,0x6FFFF).set2(0x7FFFE,0x7FFFF)
   .set2(0x8FFFE,0x8FFFF).set2(0x9FFFE,0x9FFFF).set2(0xAFFFE,0xAFFFF)
   .set2(0xBFFFE,0xBFFFF).set2(0xCFFFE,0xCFFFF).set2(0xDFFFE,0xDFFFF)
   .set2(0xEFFFE,0xEFFFF).set2(0xFFFFE,0xFFFFF).set2(0x10FFFE,0x10FFFF)
   
 // c.5
   .set2('\uD800','\uDFFF')
   
 // c.6
   .set2('\uFFF9','\uFFFD')
   
 // c.7
   .set2('\u2FF0','\u2FFB')
   
 // c.8
   .set2('\u0340').set2('\u0341').set2('\u200E')
   .set2('\u200F').set2('\u202A').set2('\u202B')
   .set2('\u202C').set2('\u202D').set2('\u202E')
   .set2('\u206A').set2('\u206B').set2('\u206C')
   .set2('\u206D').set2('\u206E').set2('\u206F')
   
 // c.9
   .set2(0xE0001).set2(0xE0020,0xE007F)
  
 // unassigned
   .set2(UNASSIGNED);
  
  
  public static final BitSet RandAL = new ChainableBitSet()
   .set2('\u05BE').set2('\u05C0').set2('\u05C3')
   .set2('\u05D0','\u05EA').set2('\u05F0','\u05F4')
   .set2('\u061B').set2('\u061F').set2('\u0621','\u063A')
   .set2('\u0640','\u064A').set2('\u066D','\u066F').set2('\u0671','\u06D5')
   .set2('\u06DD').set2('\u06E5','\u06E6').set2('\u06FA','\u06FE')
   .set2('\u0700','\u070D').set2('\u0710').set2('\u0712','\u072C')
   .set2('\u0780','\u07A5').set2('\u07B1').set2('\u200F')
   .set2('\uFB1D').set2('\uFB1F','\uFB28').set2('\uFB2A','\uFB36')
   .set2('\uFB38','\uFB3C').set2('\uFB3E').set2('\uFB40','\uFB41')
   .set2('\uFB43','\uFB44').set2('\uFB46','\uFBB1').set2('\uFBD3','\uFD3D')
   .set2('\uFD50','\uFD8F').set2('\uFD92','\uFDC7').set2('\uFDF0','\uFDFC')
   .set2('\uFE70','\uFE74').set2('\uFE76','\uFEFC');
  
  public static final BitSet LCat = new ChainableBitSet()
    .set2('\u0041','\u005A')
    .set2('\u0061','\u007A')
    .set2('\u00AA')
    .set2('\u00B5')
    .set2('\u00BA')
    .set2('\u00C0','\u00D6')
    .set2('\u00D8','\u00F6')
    .set2('\u00F8','\u0220')
    .set2('\u0222','\u0233')
    .set2('\u0250','\u02AD')
    .set2('\u02B0','\u02B8')
    .set2('\u02BB','\u02C1')
    .set2('\u02D0','\u02D1')
    .set2('\u02E0','\u02E4')
    .set2('\u02EE')
    .set2('\u037A')
    .set2('\u0386')
    .set2('\u0388','\u038A')
    .set2('\u038C')
    .set2('\u038E','\u03A1')
    .set2('\u03A3','\u03CE')
    .set2('\u03D0','\u03F5')
    .set2('\u0400','\u0482')
    .set2('\u048A','\u04CE')
    .set2('\u04D0','\u04F5')
    .set2('\u04F8','\u04F9')
    .set2('\u0500','\u050F')
    .set2('\u0531','\u0556')
    .set2('\u0559','\u055F')
    .set2('\u0561','\u0587')
    .set2('\u0589')
    .set2('\u0903')
    .set2('\u0905','\u0939')
    .set2('\u093D','\u0940')
    .set2('\u0949','\u094C')
    .set2('\u0950')
    .set2('\u0958','\u0961')
    .set2('\u0964','\u0970')
    .set2('\u0982','\u0983')
    .set2('\u0985','\u098C')
    .set2('\u098F','\u0990')
    .set2('\u0993','\u09A8')
    .set2('\u09AA','\u09B0')
    .set2('\u09B2')
    .set2('\u09B6','\u09B9')
    .set2('\u09BE','\u09C0')
    .set2('\u09C7','\u09C8')
    .set2('\u09CB','\u09CC')
    .set2('\u09D7')
    .set2('\u09DC','\u09DD')
    .set2('\u09DF','\u09E1')
    .set2('\u09E6','\u09F1')
    .set2('\u09F4','\u09FA')
    .set2('\u0A05','\u0A0A')
    .set2('\u0A0F','\u0A10')
    .set2('\u0A13','\u0A28')
    .set2('\u0A2A','\u0A30')
    .set2('\u0A32','\u0A33')
    .set2('\u0A35','\u0A36')
    .set2('\u0A38','\u0A39')
    .set2('\u0A3E','\u0A40')
    .set2('\u0A59','\u0A5C')
    .set2('\u0A5E')
    .set2('\u0A66','\u0A6F')
    .set2('\u0A72','\u0A74')
    .set2('\u0A83')
    .set2('\u0A85','\u0A8B')
    .set2('\u0A8D')
    .set2('\u0A8F','\u0A91')
    .set2('\u0A93','\u0AA8')
    .set2('\u0AAA','\u0AB0')
    .set2('\u0AB2','\u0AB3')
    .set2('\u0AB5','\u0AB9')
    .set2('\u0ABD','\u0AC0')
    .set2('\u0AC9')
    .set2('\u0ACB','\u0ACC')
    .set2('\u0AD0')
    .set2('\u0AE0')
    .set2('\u0AE6','\u0AEF')
    .set2('\u0B02','\u0B03')
    .set2('\u0B05','\u0B0C')
    .set2('\u0B0F','\u0B10')
    .set2('\u0B13','\u0B28')
    .set2('\u0B2A','\u0B30')
    .set2('\u0B32','\u0B33')
    .set2('\u0B36','\u0B39')
    .set2('\u0B3D','\u0B3E')
    .set2('\u0B40')
    .set2('\u0B47','\u0B48')
    .set2('\u0B4B','\u0B4C')
    .set2('\u0B57')
    .set2('\u0B5C','\u0B5D')
    .set2('\u0B5F','\u0B61')
    .set2('\u0B66','\u0B70')
    .set2('\u0B83')
    .set2('\u0B85','\u0B8A')
    .set2('\u0B8E','\u0B90')
    .set2('\u0B92','\u0B95')
    .set2('\u0B99','\u0B9A')
    .set2('\u0B9C')
    .set2('\u0B9E','\u0B9F')
    .set2('\u0BA3','\u0BA4')
    .set2('\u0BA8','\u0BAA')
    .set2('\u0BAE','\u0BB5')
    .set2('\u0BB7','\u0BB9')
    .set2('\u0BBE','\u0BBF')
    .set2('\u0BC1','\u0BC2')
    .set2('\u0BC6','\u0BC8')
    .set2('\u0BCA','\u0BCC')
    .set2('\u0BD7')
    .set2('\u0BE7','\u0BF2')
    .set2('\u0C01','\u0C03')
    .set2('\u0C05','\u0C0C')
    .set2('\u0C0E','\u0C10')
    .set2('\u0C12','\u0C28')
    .set2('\u0C2A','\u0C33')
    .set2('\u0C35','\u0C39')
    .set2('\u0C41','\u0C44')
    .set2('\u0C60','\u0C61')
    .set2('\u0C66','\u0C6F')
    .set2('\u0C82','\u0C83')
    .set2('\u0C85','\u0C8C')
    .set2('\u0C8E','\u0C90')
    .set2('\u0C92','\u0CA8')
    .set2('\u0CAA','\u0CB3')
    .set2('\u0CB5','\u0CB9')
    .set2('\u0CBE')
    .set2('\u0CC0','\u0CC4')
    .set2('\u0CC7','\u0CC8')
    .set2('\u0CCA','\u0CCB')
    .set2('\u0CD5','\u0CD6')
    .set2('\u0CDE')
    .set2('\u0CE0','\u0CE1')
    .set2('\u0CE6','\u0CEF')
    .set2('\u0D02','\u0D03')
    .set2('\u0D05','\u0D0C')
    .set2('\u0D0E','\u0D10')
    .set2('\u0D12','\u0D28')
    .set2('\u0D2A','\u0D39')
    .set2('\u0D3E','\u0D40')
    .set2('\u0D46','\u0D48')
    .set2('\u0D4A','\u0D4C')
    .set2('\u0D57')
    .set2('\u0D60','\u0D61')
    .set2('\u0D66','\u0D6F')
    .set2('\u0D82','\u0D83')
    .set2('\u0D85','\u0D96')
    .set2('\u0D9A','\u0DB1')
    .set2('\u0DB3','\u0DBB')
    .set2('\u0DBD')
    .set2('\u0DC0','\u0DC6')
    .set2('\u0DCF','\u0DD1')
    .set2('\u0DD8','\u0DDF')
    .set2('\u0DF2','\u0DF4')
    .set2('\u0E01','\u0E30')
    .set2('\u0E32','\u0E33')
    .set2('\u0E40','\u0E46')
    .set2('\u0E4F','\u0E5B')
    .set2('\u0E81','\u0E82')
    .set2('\u0E84')
    .set2('\u0E87','\u0E88')
    .set2('\u0E8A')
    .set2('\u0E8D')
    .set2('\u0E94','\u0E97')
    .set2('\u0E99','\u0E9F')
    .set2('\u0EA1','\u0EA3')
    .set2('\u0EA5')
    .set2('\u0EA7')
    .set2('\u0EAA','\u0EAB')
    .set2('\u0EAD','\u0EB0')
    .set2('\u0EB2','\u0EB3')
    .set2('\u0EBD')
    .set2('\u0EC0','\u0EC4')
    .set2('\u0EC6')
    .set2('\u0ED0','\u0ED9')
    .set2('\u0EDC','\u0EDD')
    .set2('\u0F00','\u0F17')
    .set2('\u0F1A','\u0F34')
    .set2('\u0F36')
    .set2('\u0F38')
    .set2('\u0F3E','\u0F47')
    .set2('\u0F49','\u0F6A')
    .set2('\u0F7F')
    .set2('\u0F85')
    .set2('\u0F88','\u0F8B')
    .set2('\u0FBE','\u0FC5')
    .set2('\u0FC7','\u0FCC')
    .set2('\u0FCF')
    .set2('\u1000','\u1021')
    .set2('\u1023','\u1027')
    .set2('\u1029','\u102A')
    .set2('\u102C')
    .set2('\u1031')
    .set2('\u1038')
    .set2('\u1040','\u1057')
    .set2('\u10A0','\u10C5')
    .set2('\u10D0','\u10F8')
    .set2('\u10FB')
    .set2('\u1100','\u1159')
    .set2('\u115F','\u11A2')
    .set2('\u11A8','\u11F9')
    .set2('\u1200','\u1206')
    .set2('\u1208','\u1246')
    .set2('\u1248')
    .set2('\u124A','\u124D')
    .set2('\u1250','\u1256')
    .set2('\u1258')
    .set2('\u125A','\u125D')
    .set2('\u1260','\u1286')
    .set2('\u1288')
    .set2('\u128A','\u128D')
    .set2('\u1290','\u12AE')
    .set2('\u12B0')
    .set2('\u12B2','\u12B5')
    .set2('\u12B8','\u12BE')
    .set2('\u12C0')
    .set2('\u12C2','\u12C5')
    .set2('\u12C8','\u12CE')
    .set2('\u12D0','\u12D6')
    .set2('\u12D8','\u12EE')
    .set2('\u12F0','\u130E')
    .set2('\u1310')
    .set2('\u1312','\u1315')
    .set2('\u1318','\u131E')
    .set2('\u1320','\u1346')
    .set2('\u1348','\u135A')
    .set2('\u1361','\u137C')
    .set2('\u13A0','\u13F4')
    .set2('\u1401','\u1676')
    .set2('\u1681','\u169A')
    .set2('\u16A0','\u16F0')
    .set2('\u1700','\u170C')
    .set2('\u170E','\u1711')
    .set2('\u1720','\u1731')
    .set2('\u1735','\u1736')
    .set2('\u1740','\u1751')
    .set2('\u1760','\u176C')
    .set2('\u176E','\u1770')
    .set2('\u1780','\u17B6')
    .set2('\u17BE','\u17C5')
    .set2('\u17C7','\u17C8')
    .set2('\u17D4','\u17DA')
    .set2('\u17DC')
    .set2('\u17E0','\u17E9')
    .set2('\u1810','\u1819')
    .set2('\u1820','\u1877')
    .set2('\u1880','\u18A8')
    .set2('\u1E00','\u1E9B')
    .set2('\u1EA0','\u1EF9')
    .set2('\u1F00','\u1F15')
    .set2('\u1F18','\u1F1D')
    .set2('\u1F20','\u1F45')
    .set2('\u1F48','\u1F4D')
    .set2('\u1F50','\u1F57')
    .set2('\u1F59')
    .set2('\u1F5B')
    .set2('\u1F5D')
    .set2('\u1F5F','\u1F7D')
    .set2('\u1F80','\u1FB4')
    .set2('\u1FB6','\u1FBC')
    .set2('\u1FBE')
    .set2('\u1FC2','\u1FC4')
    .set2('\u1FC6','\u1FCC')
    .set2('\u1FD0','\u1FD3')
    .set2('\u1FD6','\u1FDB')
    .set2('\u1FE0','\u1FEC')
    .set2('\u1FF2','\u1FF4')
    .set2('\u1FF6','\u1FFC')
    .set2('\u200E')
    .set2('\u2071')
    .set2('\u207F')
    .set2('\u2102')
    .set2('\u2107')
    .set2('\u210A','\u2113')
    .set2('\u2115')
    .set2('\u2119','\u211D')
    .set2('\u2124')
    .set2('\u2126')
    .set2('\u2128')
    .set2('\u212A','\u212D')
    .set2('\u212F','\u2131')
    .set2('\u2133','\u2139')
    .set2('\u213D','\u213F')
    .set2('\u2145','\u2149')
    .set2('\u2160','\u2183')
    .set2('\u2336','\u237A')
    .set2('\u2395')
    .set2('\u249C','\u24E9')
    .set2('\u3005','\u3007')
    .set2('\u3021','\u3029')
    .set2('\u3031','\u3035')
    .set2('\u3038','\u303C')
    .set2('\u3041','\u3096')
    .set2('\u309D','\u309F')
    .set2('\u30A1','\u30FA')
    .set2('\u30FC','\u30FF')
    .set2('\u3105','\u312C')
    .set2('\u3131','\u318E')
    .set2('\u3190','\u31B7')
    .set2('\u31F0','\u321C')
    .set2('\u3220','\u3243')
    .set2('\u3260','\u327B')
    .set2('\u327F','\u32B0')
    .set2('\u32C0','\u32CB')
    .set2('\u32D0','\u32FE')
    .set2('\u3300','\u3376')
    .set2('\u337B','\u33DD')
    .set2('\u33E0','\u33FE')
    .set2('\u3400','\u4DB5')
    .set2('\u4E00','\u9FA5')
    .set2('\uA000','\uA48C')
    .set2('\uAC00','\uD7A3')
    .set2('\uD800','\uFA2D')
    .set2('\uFA30','\uFA6A')
    .set2('\uFB00','\uFB06')
    .set2('\uFB13','\uFB17')
    .set2('\uFF21','\uFF3A')
    .set2('\uFF41','\uFF5A')
    .set2('\uFF66','\uFFBE')
    .set2('\uFFC2','\uFFC7')
    .set2('\uFFCA','\uFFCF')
    .set2('\uFFD2','\uFFD7')
    .set2('\uFFDA','\uFFDC')
    .set2(0x10300,0x1031E)
    .set2(0x10320,0x10323)
    .set2(0x10330,0x1034A)
    .set2(0x10400,0x10425)
    .set2(0x10428,0x1044D)
    .set2(0x1D000,0x1D0F5)
    .set2(0x1D100,0x1D126)
    .set2(0x1D12A,0x1D166)
    .set2(0x1D16A,0x1D172)
    .set2(0x1D183,0x1D184)
    .set2(0x1D18C,0x1D1A9)
    .set2(0x1D1AE,0x1D1DD)
    .set2(0x1D400,0x1D454)
    .set2(0x1D456,0x1D49C)
    .set2(0x1D49E,0x1D49F)
    .set2(0x1D4A2)
    .set2(0x1D4A5,0x1D4A6)
    .set2(0x1D4A9,0x1D4AC)
    .set2(0x1D4AE,0x1D4B9)
    .set2(0x1D4BB)
    .set2(0x1D4BD,0x1D4C0)
    .set2(0x1D4C2,0x1D4C3)
    .set2(0x1D4C5,0x1D505)
    .set2(0x1D507,0x1D50A)
    .set2(0x1D50D,0x1D514)
    .set2(0x1D516,0x1D51C)
    .set2(0x1D51E,0x1D539)
    .set2(0x1D53B,0x1D53E)
    .set2(0x1D540,0x1D544)
    .set2(0x1D546)
    .set2(0x1D54A,0x1D550)
    .set2(0x1D552,0x1D6A3)
    .set2(0x1D6A8,0x1D7C9)
    .set2(0x20000,0x2A6D6)
    .set2(0x2F800,0x2FA1D)
    .set2(0xF0000,0xFFFFD)
    .set2(0x100000,0x10FFFD);
}
