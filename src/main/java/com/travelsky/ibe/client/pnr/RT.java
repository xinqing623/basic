package com.travelsky.ibe.client.pnr;

import com.travelsky.ibe.client.IBEClient;
import com.travelsky.ibe.exceptions.IBEException;
import com.travelsky.util.CommandParser;
import com.travelsky.util.QDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RT extends IBEClient
{
  public RTResult retrieve(String pnrno)
    throws Exception
  {
    String[] submitInfo = new String[3];
    submitInfo[0] = "RT";
    submitInfo[1] = pnrno;
    submitInfo[2] = "RETRIEVE";
    String retmsg = null;
    try
    {
      retmsg = query(submitInfo);
    }
    catch (Exception e) {
      throw e;
    }

    return decode(retmsg);
  }

  public RTResult retrieveCompletely(String pnrno) throws Exception {
    String[] submitInfo = new String[3];
    submitInfo[0] = "RT";
    submitInfo[1] = pnrno;
    submitInfo[2] = "RETRIEVECOMPLETELYTEXTONLY";
    String retmsg = null;
    try
    {
      retmsg = query(submitInfo);
    }
    catch (Exception e) {
      throw e;
    }

    return decode(retmsg);
  }

  public static PNRFC decodeFC(String retmsg) throws Exception
  {
    try {
      PNRFC fc = new PNRFC();
      String[] fcStr = CommandParser.parse(retmsg);
      fc.setFc(fcStr[0]);
      if (fcStr[1] != null)
        try
        {
          if (fcStr[1].length() == 7)
            fc.fcDate = QDateTime.stringToDate(fcStr[1], "ddmmmyy");
          else
            fc.fcDate = 
              QDateTime.stringToDate(fcStr[1], "ddmmmyyy");
        }
        catch (Exception localException1) {
        }
      fc.pseg = fcStr[2];
      fc.pfee = Double.parseDouble(fcStr[3]);
      fc.dseg = fcStr[4];
      fc.dfee = Double.parseDouble(fcStr[5]);
      fc.hseg = fcStr[6];
      fc.hfee = Double.parseDouble(fcStr[7]);
      fc.ROE = Double.parseDouble(fcStr[8]);
      fc.extraInfo = fcStr[9];
      fc.moneytype = fcStr[10];
      fc.infant = "true".equalsIgnoreCase(fcStr[11]);
      fc.psgrid = fcStr[16];
      if (fcStr[17] != null)
        fc.total = Double.parseDouble(fcStr[17]);
      if (fcStr.length > 19)
      {
        fc.setFreeText(fcStr[19]);
        fc.setExtraInfo(fcStr[18]);
      }
      String[] segments = CommandParser.parse(fcStr[12]);
      for (int i = 0; i < segments.length; i++)
      {
        String[] seg = CommandParser.parse(segments[i]);
        String carrier = seg[0];
        String dst = seg[1];
        String farebasis = seg[2];
        String NVA = seg[3];
        String NVB = seg[4];
        String msseg = seg[5];
        int ms = Integer.parseInt(seg[6]);
        String org = seg[7];
        String F = seg[8];
        String QSeg = seg[9];
        String travelDirect = seg[10];
        boolean brchEnd = "true".equalsIgnoreCase(seg[11]);
        boolean brchStart = "true".equals(seg[12]);
        boolean E = "true".equals(seg[13]);
        double price = Double.parseDouble(seg[14]);
        double Q = Double.parseDouble(seg[15]);
        boolean stopover = "true".equals(seg[16]);
        String mileIntermediatePoint = null;
        String fareseg = null;
        if (seg.length > 17)
        {
          mileIntermediatePoint = seg[17];
          fareseg = seg[18];
        }

        fc.addFC(org, dst, carrier, farebasis, price, F, NVB, NVA, E, 
          stopover, travelDirect, ms, msseg, Q, QSeg, brchStart, 
          brchEnd, mileIntermediatePoint, fareseg);
      }

      String[] xts = CommandParser.parse(fcStr[13]);
      for (int i = 0; i < xts.length; i++)
      {
        String[] xt = CommandParser.parse(xts[i]);
        String code = xt[0];
        String currency = xt[1];
        double amount = Double.parseDouble(xt[2]);
        fc.insertTax(currency, amount, code);
      }

      String[] oxts = CommandParser.parse(fcStr[14]);
      for (int i = 0; i < oxts.length; i++)
      {
        String[] xt = CommandParser.parse(oxts[i]);
        String code = xt[0];
        String currency = xt[1];
        double amount = Double.parseDouble(xt[2]);
        fc.insertOTax(currency, amount, code);
      }

      String[] rxts = CommandParser.parse(fcStr[15]);
      for (int i = 0; i < rxts.length; i++)
      {
        String[] xt = CommandParser.parse(rxts[i]);
        String code = xt[0];
        String currency = xt[1];
        double amount = Double.parseDouble(xt[2]);
        fc.insertRTax(currency, amount, code);
      }

      return fc;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static RTResult decode(String retmsg) throws Exception
  {
    try {
      if ((retmsg.startsWith("Error")) || (retmsg == null) || 
        (retmsg.equals("")))
      {
        return null;
      }
      String[] result = CommandParser.parse(retmsg);

      if (result[0] == null)
      {
        return null;
      }
      RTResult rr = new RTResult();

      rr.pnrcode = result[0];

      String[] temp = CommandParser.parse(result[1]);
      if (temp[0].equalsIgnoreCase("TRUE"))
      {
        rr.group = true;
        rr.groupname = temp[1];
        rr.groupNumber = Integer.parseInt(temp[2]);
      }
      else {
        rr.group = false;
      }

      temp = CommandParser.parse(result[2]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRPassenger pp = new PNRPassenger();
        pp.setName(temp1[0]);
        pp.setType(Integer.parseInt(temp1[1]));
        pp.setAge(Integer.parseInt(temp1[2]));
        pp.lineIndex = Integer.parseInt(temp1[3]);

        if (temp1.length >= 5)
        {
          pp.setNameInPnr(temp1[4]);
          if (temp1.length >= 6)
            try
            {
              pp.setPassengerType(Integer.parseInt(temp1[5]));
            }
            catch (Exception localException1) {
            }
        }
        if ((temp1.length >= 7) && (temp1[6] != null))
          try
          {
            pp.setBirth(
              QDateTime.stringToDate(temp1[6], "yyyymmdd"));
          }
          catch (Exception localException2) {
          }
        rr.passengers.addElement(pp);
      }

      temp = CommandParser.parse(result[3]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRAirSeg pa = new PNRAirSeg();
        pa.setAirNo(temp1[0]);
        pa.setFltClass(temp1[1].charAt(0));
        pa.setOrgCity(temp1[2]);
        pa.setDesCity(temp1[3]);
        if (temp1[4] != null)
        {
          pa.setDepartureTime(QDateTime.stringToDate(temp1[4], 
            "YYMMDDHHMI"));
        }
        if (temp1[5] != null)
        {
          pa.setArrivalTime(QDateTime.stringToDate(temp1[5], 
            "YYMMDDHHMI"));
        }
        pa.setActionCode(temp1[6]);
        pa.setTktNum(Integer.parseInt(temp1[7]));
        pa.setSkChanged(temp1[8].equalsIgnoreCase("TRUE"));
        pa.setType(Integer.parseInt(temp1[9]));
        pa.setEtktairseg(temp1[10].equalsIgnoreCase("TRUE"));
        pa.lineIndex = Integer.parseInt(temp1[11]);
        pa.setOriginalAirSeg(temp1[12]);
        if (temp1.length >= 15)
        {
          pa.setOperateCarrier(temp1[13]);
          pa.setCodeShare("true".equals(temp1[14]));
        }
        if (temp1.length > 15)
        {
          pa.setDepartureTerm(temp1[15]);
          pa.setArrivalTerm(temp1[16]);
          pa.setSubClass(temp1[17]);
        }
        rr.airSegs.addElement(pa);
      }

      temp = CommandParser.parse(result[4]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRTkt pt = new PNRTkt();
        pt.setType(temp1[0]);
        if (temp1[1] != null)
          pt.setDateLimit(QDateTime.stringToDate(temp1[1], 
            "YYMMDDHHMI"));
        pt.setOffice(temp1[2]);
        pt.setPsgrID(temp1[3]);
        pt.setRmk(temp1[4]);
        pt.lineIndex = Integer.parseInt(temp1[5]);
        pt.setTkted("true".equalsIgnoreCase(temp1[6]));
        rr.tkts.addElement(pt);
      }

      temp = CommandParser.parse(result[5]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRTktNo pt = new PNRTktNo();
        pt.setTktNo(temp1[0]);
        pt.setPsgrID(temp1[1]);
        pt.setRemark(temp1[2]);
        pt.lineIndex = Integer.parseInt(temp1[3]);
        if (temp1.length > 4)
          pt.setConjunction(temp1[4]);
        rr.tktnos.addElement(pt);
      }

      temp = CommandParser.parse(result[6]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRRMK pr = new PNRRMK();
        pr.setRmktype(temp1[0]);
        pr.setPsgrID(temp1[1]);
        pr.setRmkinfo(temp1[2]);
        pr.lineIndex = Integer.parseInt(temp1[3]);
        rr.rmks.addElement(pr);
      }

      temp = CommandParser.parse(result[7]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRSSR ps = null;
        try
        {
          if ((temp1.length >= 7) && (temp1[6] != null))
          {
            String[] temp2 = CommandParser.parse(temp1[6]);
            if (temp1[3].equals("PSPT"))
            {
              PNRSSR_PSPT psp = new PNRSSR_PSPT();
              psp.setBirth(temp2[0]);
              psp.setNationality(temp2[1]);
              psp.setHolder(temp2[2].charAt(0));
              psp.setInfant(temp2[3].equalsIgnoreCase("true"));
              psp.setPsptNo(temp2[4]);
              psp.setPaxName(temp2[5]);
              psp.setSex(temp2[6].charAt(0));
              psp.setPersonNum(Integer.parseInt(temp2[7]));
              ps = psp;
            }
            if (temp1[3].equals("FOID"))
            {
              PNRSSR_FOID psf = new PNRSSR_FOID();
              psf.setPersonNum(Integer.parseInt(temp2[0]));
              psf.setIdtype(temp2[1]);
              psf.setIdNo(temp2[2]);
              ps = psf;
            }
            if (temp1[3].equals("DOCS"))
            {
              PNRSSR_DOCS psd = new PNRSSR_DOCS();

              psd.setBirth(temp2[0]);
              psd.setDoc_No(temp2[1]);
              psd.setDoc_type(temp2[2]);
              psd.setExpiry_Date(temp2[3]);
              psd.setFirstname(temp2[4]);
              psd.setGender(temp2[5]);
              psd.setHolder(temp2[6]);
              psd.setInfant(temp2[7]);
              psd.setIssueCountry(temp2[8]);
              psd.setMidname(temp2[9]);
              psd.setNationality(temp2[10]);
              psd.setPersonNum(Integer.parseInt(temp2[11]));
              psd.setSurname(temp2[12]);
              ps = psd;
            }

            if (temp1[3].equals("TKNE"))
            {
              PNRSSR_TKNE pst = new PNRSSR_TKNE();
              pst.setCabin(temp2[0].charAt(0));
              pst.setCitypair(temp2[1]);
              pst.setCouponOtherInfo(temp2[2]);
              pst.setDate(temp2[3]);
              pst.setFlightNo(temp2[4]);
              pst.setInfanttkt("true".equalsIgnoreCase(temp2[5]));
              pst.setTktIndex(temp2[7]);
              pst.setPersonNum(Integer.parseInt(temp2[6]));
              pst.setTktNo(temp2[8]);

              ps = pst;
            }
            if (temp1[3].equals("FQTV"))
            {
              PNRSSR_FQTV psf = new PNRSSR_FQTV();

              psf.setPersonNum(Integer.parseInt(temp2[0]));
              psf.setCardLvl(temp2[1]);
              psf.setCardNo(temp2[2]);
              psf.setCitypair(temp2[3]);
              psf.setDate(temp2[4]);
              psf.setFlight(temp2[5]);
              psf.setCabin(temp2[6].charAt(0));
              ps = psf;
            }
            if (temp1[3].equals("RQST"))
            {
              PNRSSR_RQST psr = new PNRSSR_RQST();
              psr.setCitypair(temp2[0]);
              psr.setDate(temp2[1]);
              psr.setFlight(temp2[2]);
              psr.setCabin(temp2[3].charAt(0));
              psr.setSeatNo(temp2[4]);
              psr.setPersonNum(Integer.parseInt(temp2[5]));
              ps = psr;
            }
            if (temp1[3].equals("SEAT"))
            {
              PNRSSR_SEAT pss = new PNRSSR_SEAT();
              pss.setCabin(temp2[0].charAt(0));
              pss.setCitypair(temp2[1]);
              pss.setDate(temp2[2]);
              pss.setFlight(temp2[3]);
              pss.setSeatNo(temp2[4]);
              pss.setPersonNum(Integer.parseInt(temp2[5]));
              ps = pss;
            }
          }
        }
        catch (Exception localException3) {
        }
        if (ps == null)
          ps = new PNRSSR();
        ps.setServeInfo(temp1[1]);
        ps.setPsgrID(temp1[0]);
        ps.lineIndex = Integer.parseInt(temp1[2]);
        ps.SSRType = temp1[3];
        ps.airline = temp1[4];
        ps.actionCode = temp1[5];

        rr.ssrs.addElement(ps);
      }

      temp = CommandParser.parse(result[8]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNROSI po = new PNROSI();
        po.setOsi(temp1[0]);
        po.setPNum(temp1[1]);
        po.lineIndex = Integer.parseInt(temp1[2]);
        rr.osis.addElement(po);
      }

      temp = CommandParser.parse(result[9]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRContact pc = new PNRContact();
        pc.setCity(temp1[0]);
        pc.setContact(temp1[1]);
        pc.setPsgrID(temp1[2]);
        pc.lineIndex = Integer.parseInt(temp1[3]);
        rr.contacts.addElement(pc);
      }

      temp = CommandParser.parse(result[10]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRInfant pinf = new PNRInfant();
        pinf.setName(temp1[0]);
        if (temp1[1] != null)
        {
          pinf.setBrith(
            QDateTime.stringToDate(temp1[1], "YYMMDDHHMI"));
        }
        pinf.setCarrier(temp1[2]);
        pinf.lineIndex = Integer.parseInt(temp1[3]);
        if (temp1.length >= 5)
          pinf.setNameInPnr(temp1[4]);
        rr.infants.addElement(pinf);
      }

      temp = CommandParser.parse(result[11]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNROther po = new PNROther();
        po.setOther(temp1[0]);
        po.lineIndex = Integer.parseInt(temp1[1]);
        rr.others.addElement(po);
        if ((temp1[0].startsWith("FC")) && (temp1.length >= 3))
        {
          PNRFC fc = decodeFC(temp1[2]);
          fc.index = Integer.parseInt(temp1[1]);
          rr.fcs.add(fc);
        } else if ((temp1[0].startsWith("FN")) && (temp1.length >= 5))
        {
          PNRFN fn = decodeFN(temp1[2]);
          fn.index = Integer.parseInt(temp1[1]);
          fn.psgrid = temp1[3];
          fn.infant = "true".equals(temp1[4]);
          fn.fn = temp1[0];
          rr.fns.add(fn);
        } else if ((temp1[0].startsWith("FP")) && (temp1.length >= 6))
        {
          PNRFP fp = new PNRFP();
          fp.paytype = temp1[2];
          fp.currency = temp1[3];
          fp.lineIndex = Integer.parseInt(temp1[1]);
          fp.infant = "true".equalsIgnoreCase(temp1[5]);
          fp.psgrid = temp1[4];
          fp.remark = temp1[0];
          rr.fps.add(fp);
        } else if ((temp1[0].startsWith("OI")) && (temp1.length >= 3))
        {
          PNROI oi = new PNROI();
          oi.lineIndex = Integer.parseInt(temp1[1]);
          oi.oi = temp1[0];
          oi.psgrid = temp1[2];
          oi.infant = "true".equalsIgnoreCase(temp1[3]);
          rr.ois.add(oi);
        } else if ((temp1[0].startsWith("EI")) && (temp1.length >= 3))
        {
          PNREI ei = new PNREI();
          ei.lineIndex = Integer.parseInt(temp1[1]);

          ei.ei = temp1[0];
          ei.infant = "true".equalsIgnoreCase(temp1[3]);
          ei.psgrid = temp1[2];
          rr.eis.add(ei);
        } else if ((temp1[0].startsWith("TC")) && (temp1.length >= 4))
        {
          PNRTC tc = new PNRTC();
          tc.lineIndex = Integer.parseInt(temp1[1]);

          tc.tourcode = temp1[3];
          tc.psgrid = temp1[2];
          tc.infant = "true".equalsIgnoreCase(temp1[4]);
          rr.tcs.add(tc);
        }
      }

      temp = CommandParser.parse(result[12]);
      PNRResp pr = new PNRResp();
      pr.setCrssign(temp[0]);
      pr.setOfficecode(temp[1]);
      pr.setPnrno(temp[2]);
      pr.setRemark(temp[3]);
      pr.lineIndex = Integer.parseInt(temp[4]);
      rr.resp = pr;

      temp = CommandParser.parse(result[13]);
      for (int i = 0; i < temp.length; i++)
      {
        String[] temp1 = CommandParser.parse(temp[i]);
        PNRBA pb = new PNRBA();
        pb.setBAInfo(temp1[0]);
        pb.setPsgrid(temp1[1]);
        pb.lineIndex = Integer.parseInt(temp1[2]);
        rr.bas.addElement(pb);
      }

      if (result.length > 14)
        rr.setOringinalRT(result[14]);
      rr.passengerNumber = rr.passengers.size();

      if (result.length > 15)
        try
        {
          rr.flag = Integer.parseInt(result[15]);
        }
        catch (Exception localException4) {
        }
      return rr;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    throw new IBEException(
      "Error occured. May be data lost while transfering.");
  }

  public PNREnvelop retrieveFirstEnvelop(String pnrno) throws IBEException {
    String[] submitInfo = new String[3];
    submitInfo[0] = "RT";
    submitInfo[1] = pnrno;
    submitInfo[2] = "FIRSTUPD";
    String retmsg = null;
    try
    {
      retmsg = query(submitInfo);
      String[] msg = CommandParser.parse(retmsg);

      PNREnvelop env = new PNREnvelop();
      env.setAgent(msg[2]);
      env.setOffice(msg[1]);
      env.setEnvelopIdx(Integer.parseInt(msg[0]));
      env.setEnvelopTime(
        QDateTime.stringToCalendar(msg[3], "ddmmmyyhhmi"));
      env.setEnvelopType(msg[4]);

      return env;
    }
    catch (Exception e) {
      throw new IBEException(e);
    }
  }

  public static PNRFN decodeFN(String retmsg) throws Exception
  {
    try {
      String[] fnStr = CommandParser.parse(retmsg);
      String[] item = CommandParser.parse(fnStr[0]);
      String fcurrency = item[0];
      double f = Double.parseDouble(item[1]);
      item = CommandParser.parse(fnStr[1]);
      String rcurrency = item[0];
      double r = Double.parseDouble(item[1]);
      item = CommandParser.parse(fnStr[2]);
      String ecurrency = item[0];
      double e = Double.parseDouble(item[1]);
      item = CommandParser.parse(fnStr[3]);
      String xcurrency = item[0];
      double x = Double.parseDouble(item[1]);
      item = CommandParser.parse(fnStr[4]);
      String acurrency = item[0];
      double a = Double.parseDouble(item[1]);
      item = CommandParser.parse(fnStr[8]);
      String scurrency = item[0];
      double s = Double.parseDouble(item[1]);
      String fntype = "";
      if (fnStr.length > 9)
        fntype = fnStr[9];
      String bcurrency = "";
      double b = -2.0D;
      if (fnStr.length > 10)
      {
        item = CommandParser.parse(fnStr[10]);
        bcurrency = item[0];
        b = Double.parseDouble(item[1]);
      }

      ArrayList t = null;
      ArrayList tcode = null;
      ArrayList ot = null;
      ArrayList otcode = null;
      item = CommandParser.parse(fnStr[5]);
      String tcurrency = null;
      if ((item != null) && (item.length != 0))
      {
        t = new ArrayList();
        tcode = new ArrayList();
        for (int i = 0; i < item.length; i++)
        {
          String[] tax = CommandParser.parse(item[i]);
          if (tcurrency == null)
            tcurrency = tax[0];
          t.add(Double.valueOf(tax[1]));
          tcode.add(tax[2]);
        }
      }

      String otcurrency = null;
      item = CommandParser.parse(fnStr[6]);
      if ((item != null) && (item.length != 0))
      {
        ot = new ArrayList();
        otcode = new ArrayList();
        for (int i = 0; i < item.length; i++)
        {
          String[] tax = CommandParser.parse(item[i]);
          if (otcurrency == null)
            otcurrency = tax[0];
          ot.add(Double.valueOf(tax[1]));
          otcode.add(tax[2]);
        }
      }
      double c = Double.parseDouble(fnStr[7]);

      PNRFN fn = new PNRFN();
      if (ecurrency.length() <= 0)
      {
        rcurrency.length();
      }

      fn.setAmount(1, fcurrency, f);
      fn.setAmount(4, rcurrency, r);
      fn.setAmount(32, scurrency, s);
      fn.setAmount(2, ecurrency, e);
      fn.setAmount(16, xcurrency, x);
      fn.setAmount(8, acurrency, a);
      fn.setC(c);
      fn.setAmount(64, bcurrency, b);
      fn.setType(fntype);

      if (t != null)
        for (int i = 0; i < t.size(); i++)
        {
          double d = ((Double)t.get(i)).doubleValue();
          if (Math.abs(d - -1.0D) < 0.001D)
            fn.addTax(65536, "", d, (String)tcode.get(i));
          else
            fn.addTax(65536, tcurrency, d, (String)tcode.get(i));
        }
      if (ot != null)
        for (int i = 0; i < ot.size(); i++)
          fn.addTax(32768, otcurrency, 
            ((Double)ot.get(i)).doubleValue(), 
            (String)otcode.get(i));
      return fn;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  protected Object decodeResponse(String serverStr) throws Exception {
    return decode(serverStr);
  }
}