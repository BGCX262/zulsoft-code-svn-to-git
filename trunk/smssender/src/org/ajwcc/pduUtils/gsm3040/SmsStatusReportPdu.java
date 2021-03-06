
package org.ajwcc.pduUtils.gsm3040;

import java.util.*;

//PduUtils Library - A Java library for generating GSM 3040 Protocol Data Units (PDUs)
//
//Copyright (C) 2008, Ateneo Java Wireless Competency Center/Blueblade Technologies, Philippines.
//PduUtils is distributed under the terms of the Apache License version 2.0
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
public class SmsStatusReportPdu extends Pdu
{
	// can only create via the factory
	SmsStatusReportPdu()
	{
	}

	// ==================================================
	// FIRST OCTET UTILITIES
	// ==================================================
	public void setTpMms(int value)
	{
		checkTpMti(new int[] { PduUtils.TP_MTI_SMS_DELIVER, PduUtils.TP_MTI_SMS_STATUS_REPORT });
		// for SMS-DELIVER and SMS-STATUS-REPORT only
		setFirstOctetField(PduUtils.TP_MMS_MASK, value, new int[] { PduUtils.TP_MMS_MORE_MESSAGES, PduUtils.TP_MMS_NO_MESSAGES });
	}

	public boolean hasTpMms()
	{
		checkTpMti(new int[] { PduUtils.TP_MTI_SMS_DELIVER, PduUtils.TP_MTI_SMS_STATUS_REPORT });
		// for SMS-DELIVER and SMS-STATUS-REPORT only
		return getFirstOctetField(PduUtils.TP_MMS_MASK) == PduUtils.TP_MMS_MORE_MESSAGES;
	}

	public void setTpSri(int value)
	{
		setFirstOctetField(PduUtils.TP_SRI_MASK, value, new int[] { PduUtils.TP_SRI_NO_REPORT, PduUtils.TP_SRI_REPORT });
	}

	public boolean hasTpSri()
	{
		return getFirstOctetField(PduUtils.TP_SRI_MASK) == PduUtils.TP_SRI_REPORT;
	}

	// ==================================================
	// MESSAGE REFERENCE
	// ==================================================
	// usually just 0x00 to let MC supply
	private int messageReference = 0x00;

	public void setMessageReference(int reference)
	{
		messageReference = reference;
	}

	public int getMessageReference()
	{
		return messageReference;
	}

	// ==================================================
	// STATUS
	// ==================================================
	private int status = 0x00;

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	// ==================================================
	// TIMESTAMP
	// ==================================================
	private Date timestamp;

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
	}

	public Date getTimestamp()
	{
		return this.timestamp;
	}

	// ==================================================
	// DISCHARGE TIME
	// ==================================================
	private Date dischargeTime;

	public void setDischargeTime(Date dischargeTime)
	{
		this.dischargeTime = dischargeTime;
	}

	public Date getDischargeTime()
	{
		return dischargeTime;
	}

	@Override
	protected String pduSubclassInfo()
	{
		StringBuffer sb = new StringBuffer();
		// message reference
		sb.append("Message Reference: " + PduUtils.byteToPdu(getMessageReference()));
		sb.append("\n");
		// destination address
		sb.append("Destination Address: [Length: " + getAddress().length() + " (" + PduUtils.byteToPdu((byte) getAddress().length()) + ")");
		sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte) getAddressType()) + ")");
		sb.append(", Address: " + getAddress());
		sb.append("]");
		sb.append("\n");
		// timestamp
		sb.append("TP-SCTS: " + getTimestamp());
		sb.append("\n");
		// discharge time
		sb.append("Discharge Time: " + getDischargeTime());
		sb.append("\n");
		// status
		sb.append("Status: " + PduUtils.byteToPdu(getStatus()));
		sb.append("\n");
		return sb.toString();
	}
}
