package vico.xin.mvpdemo.dto;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Contact2 {
	public String name;
	public String phone;
	public long callTimes;
	public long lastCallTimeMillis;
	public long vesion;
	public long contactID;
	@Generated(hash = 2140842589)
	public Contact2(String name, String phone, long callTimes,
			long lastCallTimeMillis, long vesion, long contactID) {
		this.name = name;
		this.phone = phone;
		this.callTimes = callTimes;
		this.lastCallTimeMillis = lastCallTimeMillis;
		this.vesion = vesion;
		this.contactID = contactID;
	}
	@Generated(hash = 124529582)
	public Contact2() {
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public long getCallTimes() {
		return this.callTimes;
	}
	public void setCallTimes(long callTimes) {
		this.callTimes = callTimes;
	}
	public long getLastCallTimeMillis() {
		return this.lastCallTimeMillis;
	}
	public void setLastCallTimeMillis(long lastCallTimeMillis) {
		this.lastCallTimeMillis = lastCallTimeMillis;
	}
	public long getVesion() {
		return this.vesion;
	}
	public void setVesion(long vesion) {
		this.vesion = vesion;
	}
	public long getContactID() {
		return this.contactID;
	}
	public void setContactID(long contactID) {
		this.contactID = contactID;
	}
}
