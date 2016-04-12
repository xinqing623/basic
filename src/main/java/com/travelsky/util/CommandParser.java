package com.travelsky.util;

import java.util.List;
import java.util.StringTokenizer;

public class CommandParser
{
  public static String decString(String s)
    throws Exception
  {
    if ((s == null) || (s.equals("")))
      throw new Exception("BAD COMMAND, DECODE ERROR");
    if (s.equals("%00"))
      return null;
    if (s.equals("%01")) {
      return "";
    }
    return replace(replace(s, "%20", " "), "%25", "%");
  }

  public static String encString(String s)
  {
    if (s == null)
      return "%00";
    if (s.equals("")) {
      return "%01";
    }
    return replace(replace(s, "%", "%25"), " ", "%20");
  }

  public static String encode(String[] as)
  {
    if (as == null)
      return null;
    StringBuffer stringbuffer = new StringBuffer();
    for (int i = 0; i < as.length; i++)
    {
      if (i != 0)
        stringbuffer.append(" ");
      stringbuffer.append(encString(as[i]));
    }

    return stringbuffer.toString();
  }

  public static String[] parse(String s)
    throws Exception
  {
    if (s == null)
      return null;
    StringTokenizer stringtokenizer = new StringTokenizer(s, " ");
    String[] as = new String[stringtokenizer.countTokens()];
    int i = 0;
    while (stringtokenizer.hasMoreTokens())
      as[(i++)] = decString(stringtokenizer.nextToken().toString());
    return as;
  }

  public static String replace(String s, String s1, String s2)
  {
    if (s == null)
      return null;
    if (s.equals(""))
      return "";
    if ((s1 == null) || (s1.equals("")))
      return s;
    if (s2 == null)
      s2 = "";
    if (s.indexOf(s1) < 0)
      return s;
    StringBuffer stringbuffer = new StringBuffer();
    int i = 0;
    for (int j = 0; j != -1; )
    {
      j = s.indexOf(s1, i);

      if (j == -1)
      {
        stringbuffer.append(s.substring(i));
      }
      else {
        stringbuffer.append(s.substring(i, j));
        stringbuffer.append(s2);
        i = j + s1.length();
      }
    }

    return stringbuffer.toString();
  }

  public static String encode(int[] is) {
    if (is == null)
      return null;
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < is.length; i++)
    {
      sb.append((i > 0 ? "|" : "") + is[i]);
    }
    return sb.toString();
  }
  public static int[] parseI(String s) throws Exception {
    if (s == null)
      return null;
    StringTokenizer st = new StringTokenizer(s, "|");
    int[] is = new int[st.countTokens()];
    int i = 0;
    while (st.hasMoreTokens()) {
      try {
        is[(i++)] = Integer.parseInt(st.nextToken());
      } catch (Exception e) {
        throw e;
      }
    }
    return is;
  }
  public static String encodeList(List list, int start, int end, boolean remove) throws Exception {
    if (list == null)
      throw new IllegalArgumentException("list in null");
    if ((end < start) || (end < 0) || (start < 0) || (start > list.size()) || (end > list.size()))
      throw new IllegalArgumentException("list size is " + list.size() + " start = " + start + " end = " + end);
    if (end == start)
      return "";
    if (start == list.size())
      throw new IllegalArgumentException("list size is " + list.size() + " start = " + start + " end = " + end);
    String[] strings = new String[end - start];

    if (!remove)
      for (int i = 0; i < end - start; i++)
      {
        strings[i] = String.valueOf(list.get(start + i));
      }
    else {
      for (int i = 0; i < end - start; i++) {
        strings[i] = String.valueOf(list.remove(start));
      }
    }

    return encode(strings);
  }
  public static String encodeList1(List list, int start, int end, boolean remove) throws Exception {
    if (list == null)
      throw new IllegalArgumentException("list in null");
    if ((end < start) || (end < 0) || (start < 0) || (start > list.size()) || (end > list.size()))
      throw new IllegalArgumentException("list size is " + list.size() + " start = " + start + " end = " + end);
    if (end == start)
      return "";
    if (start == list.size())
      throw new IllegalArgumentException("list size is " + list.size() + " start = " + start + " end = " + end);
    String[] strings = new String[end - start];

    if (!remove)
      for (int i = 0; i < end - start; i++)
      {
        strings[i] = String.valueOf(list.get(start + i));
      }
    else {
      for (int i = 0; i < end - start; i++) {
        strings[i] = String.valueOf(list.remove(start));
      }
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < strings[i].length(); i++)
    {
      sb.append("response:");
      sb.append(i);
      sb.append("/\r\n");
      sb.append(strings[i]);
      sb.append("\r\n");
    }

    return sb.toString();
  }
}