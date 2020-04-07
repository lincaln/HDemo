package com.emt.card.db.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "card_period")
public class CardPeriod extends BaseModel implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6720039209577811780L;

	/**
	 * @author 青风侠
	 * 0.创建create
	 * 1.激活activate
	 * 2.绑定bing
	 * 3.作废cancel
	 */
	public enum Timing{
		create(true,false),activate(true,true),bing(true,true),cancel(false,true);
		
		public boolean hasBigin;//可以用于开始
		
		public boolean hasEnd;//可以用于结束

		private Timing(boolean hasBigin, boolean hasEnd) {
			this.hasBigin = hasBigin;
			this.hasEnd = hasEnd;
		}
		public void valueOf() {
			// TODO Auto-generated method stub

		}
	}
	
	private Timing bigin; //开始点
	private Timing end;//结束点
	
	private Integer quantum;//时间段
	private String quantumUnit;//时间段日期
	
	private LocalDate periodDate;//有效期

	public Timing getBigin() {
		return bigin;
	}

	public void setBigin(Timing bigin) {
		this.bigin = bigin;
	}

	public Timing getEnd() {
		return end;
	}

	public void setEnd(Timing end) {
		this.end = end;
	}

	public Integer getQuantum() {
		return quantum;
	}

	public void setQuantum(Integer quantum) {
		this.quantum = quantum;
	}

	public String getQuantumUnit() {
		return quantumUnit;
	}

	public void setQuantumUnit(String quantumUnit) {
		this.quantumUnit = quantumUnit;
	}

	public LocalDate getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(LocalDate periodDate) {
		this.periodDate = periodDate;
	}
	
	
}
