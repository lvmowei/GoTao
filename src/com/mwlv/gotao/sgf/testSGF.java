package com.mwlv.gotao.sgf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testSGF {

	static List<String> idBuffer = new ArrayList<String>();
	static List<String> valueBuffer = new ArrayList<String>();

	static String sgfStr = "(;FF[4]GM[1]SZ[19]FG[257:Figure 1]PM[1]PB[Takemiya Masaki]"
			+ "BR[9 dan]PW[Cho Chikun]WR[9 dan]RE[W+Resign]KM[5.5]TM[28800]"
			+ "DT[1996-10-18,19]EV[21st Meijin]RO[2 (final)]SO[Go World #78]" + "US[Arno Hollosi];"
			+ "B[pd];W[dp];B[pp];W[dd];B[pj];W[nc];B[oe];W[qc];B[pc];W[qd]"
			+ "(;B[qf];W[rf];B[rg];W[re];B[qg];W[pb];B[ob];W[qb]"
			+ "(;B[mp];W[fq];B[ci];W[cg];B[dl];W[cn];B[qo];W[ec];B[jp];W[jd]"
			+ ";B[ei];W[eg];B[kk]LB[qq:a][dj:b][ck:c][qp:d]N[Figure 1]"
			+ ";W[me]FG[257:Figure 2];B[kf];W[ke];B[lf];W[jf];B[jg]"
			+ "(;W[mf];B[if];W[je];B[ig];W[mg];B[mj];W[mq];B[lq];W[nq]"
			+ "(;B[lr];W[qq];B[pq];W[pr];B[rq];W[rr];B[rp];W[oq];B[mr];W[oo];B[mn]"
			+ "(;W[nr];B[qp]LB[kd:a][kh:b]N[Figure 2]"
			+ ";W[pk]FG[257:Figure 3];B[pm];W[oj];B[ok];W[qr];B[os];W[ol];B[nk];W[qj]"
			+ ";B[pi];W[pl];B[qm];W[ns];B[sr];W[om];B[op];W[qi];B[oi]"
			+ "(;W[rl];B[qh];W[rm];B[rn];W[ri];B[ql];W[qk];B[sm];W[sk];B[sh];W[og]"
			+ ";B[oh];W[np];B[no];W[mm];B[nn];W[lp];B[kp];W[lo];B[ln];W[ko];B[mo]"
			+ ";W[jo];B[km]N[Figure 3])"
			+ "(;W[ql]VW[ja:ss]FG[257:Dia. 6]MN[1];B[rm];W[ph];B[oh];W[pg];B[og];W[pf]"
			+ ";B[qh];W[qe];B[sh];W[of];B[sj]TR[oe][pd][pc][ob]LB[pe:a][sg:b][si:c]"
			+ "N[Diagram 6]))" + "(;W[no]VW[jj:ss]FG[257:Dia. 5]MN[1];B[pn]N[Diagram 5]))"
			+ "(;B[pr]FG[257:Dia. 4]MN[1];W[kq];B[lp];W[lr];B[jq];W[jr];B[kp];W[kr];B[ir]"
			+ ";W[hr]LB[is:a][js:b][or:c]N[Diagram 4]))"
			+ "(;W[if]FG[257:Dia. 3]MN[1];B[mf];W[ig];B[jh]LB[ki:a]N[Diagram 3]))"
			+ "(;W[oc]VW[aa:sk]FG[257:Dia. 2]MN[1];B[md];W[mc];B[ld]N[Diagram 2]))"
			+ "(;B[qe]VW[aa:sj]FG[257:Dia. 1]MN[1];W[re];B[qf];W[rf];B[qg];W[pb];B[ob]"
			+ ";W[qb]LB[rg:a]N[Diagram 1]))";

	private int parseSGF(String sgfStr, int fromPos) {
		String sgfStrfromPos = sgfStr.substring(fromPos);

		while (true) {
			fromPos = parseGameTree(sgfStr, fromPos);
			fromPos += skipSpaceChars(sgfStrfromPos);
			sgfStrfromPos = sgfStr.substring(fromPos);
			if (!sgfStrfromPos.startsWith("("))
				break;
		}

		return fromPos;
	}

	private int parseGameTree(String sgfStr, int fromPos) {

		String sgfStrfromPos = sgfStr.substring(fromPos);
		// pContext->treeIndex++;
		// if(pContext->pfnOnTree)
		// pContext->pfnOnTree(pContext, szFromPos, pContext->treeIndex);

		fromPos++;
		sgfStrfromPos = sgfStr.substring(fromPos);
		fromPos += skipSpaceChars(sgfStrfromPos);

		char c = sgfStrfromPos.charAt(fromPos);
		while (true) {
			if (c == '(')
				fromPos = parseGameTree(sgfStr, fromPos);
			else
				fromPos = parseNodeSequence(sgfStr, fromPos);

			sgfStrfromPos = sgfStr.substring(fromPos);
			fromPos += skipSpaceChars(sgfStrfromPos);
			c = sgfStrfromPos.charAt(fromPos);

			if (c == ')') {
				break;
			}
		}

		return (fromPos + 1);
	}

	private int parseNodeSequence(String sgfStr, int fromPos) {

		String sgfStrfromPos = sgfStr.substring(fromPos);

		while (true) {
			fromPos = parseNode(sgfStr, fromPos);
			fromPos += skipSpaceChars(sgfStrfromPos);
			sgfStrfromPos = sgfStr.substring(fromPos);
			if (sgfStrfromPos.charAt(0) != ';') {
				// if(pContext->pfnOnNodeEnd)
				// pContext->pfnOnNodeEnd(pContext);
				break;
			}
		}
		return fromPos;
	}

	private int parseNode(String sgfStr, int fromPos) {

		String sgfStrfromPos = sgfStr.substring(fromPos);

		// if(pContext->pfnOnNode)
		// pContext->pfnOnNode(pContext, szFromPos);

		if (sgfStrfromPos.charAt(0) == ';') {
			fromPos++;
			sgfStrfromPos = sgfStr.substring(fromPos);
		}

		while (true) {
			fromPos += skipSpaceChars(sgfStrfromPos);
			if (sgfStr.charAt(fromPos) == '\0' || findchar(";)(", sgfStr.charAt(fromPos)) >= 0)
				break;
			fromPos = parseProperty(sgfStr, fromPos);
			sgfStrfromPos = sgfStr.substring(fromPos);
		}
		return fromPos;
	}

	private int findchar(String sz, char c) {
		return sz.indexOf(c);
	}

	private int parseProperty(String sgfStr, int fromPos) {

		int lindex;
		// int nIDBufferSize = idBuffer.size() - 1;
		String sgfStrfromPos = sgfStr.substring(fromPos);

		lindex = findchar(sgfStrfromPos, '[');

		if (lindex > 0) {
			idBuffer.add(sgfStrfromPos.substring(0, lindex));

			fromPos = parsePropertyValue(sgfStr, fromPos + lindex);
			sgfStrfromPos = sgfStr.substring(fromPos);
			while (true) {
				fromPos += skipSpaceChars(sgfStrfromPos);
				if (sgfStr.charAt(fromPos) != '[')
					break;
				fromPos = parsePropertyValue(sgfStr, fromPos);
				sgfStrfromPos = sgfStr.substring(fromPos);
			}
			return fromPos;
		}
		return -1;
	}

	boolean isTextPropertyID(String szID) {
		String[] textIDs = { "C", "N", "AP", "CA", "AN", "BR", "BT", "CP", "DT", "EV", "GN", "GC",
				"ON", "OT", "PB", "PC", "PW", "RE", "RO", "RU", "SO", "US", "WR", "WT", "FG", "LB" };
		for (int i = 0; i < textIDs.length; i++)
			if (textIDs[i].equals(szID))
				return true;
		return false;
	}

	private int parsePropertyValue(String sgfStr, int fromPos) {
		String sgfStrfromPos = sgfStr.substring(fromPos);

		if (!isTextPropertyID(idBuffer.get(0))) {
			int rindex = findchar(sgfStrfromPos, ']');
			int nNeedBufferSize = rindex;

			valueBuffer.add(sgfStrfromPos.substring(1, nNeedBufferSize));

			// if(pContext->pfnOnProperty)
			// pContext->pfnOnProperty(pContext, pContext->idBuffer,
			// pContext->valueBuffer);

			return (fromPos + rindex + 1);
		} else {
			// parse the text or simple-text value, consider the '\' escape
			// character
			String s = sgfStrfromPos.substring(1);
			char c;
			int in_escape = 0;
			int valuelen = 0;

			while (true) {
				c = s.charAt(0);

				if (in_escape == 0) {
					if (c == '\\') {
						in_escape = 1;
					} else if (c == ']') {
						break;
					} else {
						// valueBuffer.get(0).concat(c);
					}
				} else {
					// ignore the newline after '\'
					if (c != '\r' && c != '\n') {
						// getEnoughBuffer(pContext, valuelen + 1);
						// pContext->valueBuffer[valuelen++] = c;
					} else {
						char nc = s.charAt(1);
						if (nc != '\0') {
							if ((c == '\r' && nc == '\n') || (c == '\n' && nc == '\r'))
								s = s.substring(1);
						}
					}
					in_escape = 0;
				}
				s = s.substring(1);
			}
			// getEnoughBuffer(pContext, valuelen + 1);
			// pContext->valueBuffer[valuelen] = '\0';

			// if(pContext->pfnOnProperty)
			// pContext->pfnOnProperty(pContext, pContext->idBuffer,
			// pContext->valueBuffer);
			//
			return (sgfStr.indexOf(s) + 1);
		}
	}

	/**
	 * return the first non-space char's index
	 * 
	 * @param sgfStrfromPos
	 * @return
	 */
	private int skipSpaceChars(String sgfStrfromPos) {

		for (int i = 0; i < sgfStrfromPos.length(); i++) {
			if (sgfStrfromPos.charAt(i) != ' ')
				return i;
		}
		return -1;
	}

	public static void main(String[] args) {

		String[] ds = sgfStr.split(";");
		for (int i = 0; i < ds.length; i++)
			System.out.printf(ds[i] + '\n');

		// t.parseSGF(sgfStr, 0);

		// String regex="\\((.+)\\)";
		//
		// Pattern pattern = Pattern.compile(regex);
		// Matcher matcher = pattern.matcher(sgfStr);
		// if (matcher.matches()) {
		// String group = matcher.group(1);
		// System.out.println(group);
		//
		// regex="(.+)[\\(.+\\)]*";
		// pattern = Pattern.compile(regex);
		// matcher = pattern.matcher(group);
		// matcher.matches();
		// System.out.println(matcher.group(1));
		// System.out.println(matcher.group(2));
		// }else {
		// System.out.println("no matches!!");
		// }

	}

}
