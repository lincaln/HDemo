package com.emt.card.support;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.emt.card.base.Pagination;
import com.emt.card.dao.BaseDao;
import com.emt.card.db.entity.CardPeriod;
import com.emt.card.db.entity.CardPeriod.Timing;


public class BaseSerivce{
	
	@Autowired
	protected BaseDao baseDao;

	protected <T> Pagination<T> toPagination(Page<T> p){
		return new Pagination<T>(p.getNumber(), p.getSize(), p.getTotalElements(), p.getContent());
	}
	
	public class TimingDate{
		
		private Date endActivateDate;
		
		private Date endBingDate;
		
		private Date endUseDate;

		public Date getEndActivateDate() {
			return endActivateDate;
		}

		public void setEndActivateDate(Date endActivateDate) {
			this.endActivateDate = endActivateDate;
		}

		public Date getEndBingDate() {
			return endBingDate;
		}

		public void setEndBingDate(Date endBingDate) {
			this.endBingDate = endBingDate;
		}

		public Date getEndUseDate() {
			return endUseDate;
		}

		public void setEndUseDate(Date endUseDate) {
			this.endUseDate = endUseDate;
		}
		
		
	}
	private Date LocalDateToUdate(LocalDate localDate) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDate.atStartOfDay().
	    		atZone(zone).toInstant();
	    Date date = Date.from(instant);
		return date;
	}
	
	private Date intToUdate(Date initDate,Integer quantum,String unit) {
	    Date date = null;
	    if("日".equals(unit)) {
	    	date =DateUtils.addDays(initDate, quantum);
	    }
	    if("月".equals(unit)) {
	    	date =DateUtils.addMonths(initDate, quantum);
	    }
	    if("日".equals(unit)) {
	    	date =DateUtils.addYears(initDate, quantum);
	    }
		return date;
	}
	
	protected TimingDate getPeriod(List<CardPeriod> cardPeriodList,
			Date createDate,Date activateDate,Date bingDate) {
		TimingDate td=new TimingDate();
		List<CardPeriod> toTimesList=new ArrayList<CardPeriod>();
		
		for (CardPeriod cardPeriod : cardPeriodList) {
			if(cardPeriod.getBigin()==null&&cardPeriod.getPeriodDate()!=null) {
				Date date=LocalDateToUdate(cardPeriod.getPeriodDate());	
				
				if(Timing.activate.equals(cardPeriod.getEnd())
						&&(td.getEndActivateDate()==null||date.before(td.getEndActivateDate()))) {
						td.setEndActivateDate(date);
				}
			
				if(Timing.bing.equals(cardPeriod.getEnd())
						&&(td.getEndBingDate()==null||date.before(td.getEndBingDate()))) {
						td.setEndBingDate(date);;
				}
				
				if(Timing.cancel.equals(cardPeriod.getEnd())
					&&(td.getEndUseDate()==null||date.before(td.getEndUseDate()))) {
						td.setEndUseDate(date);;
				}
			}else {
				toTimesList.add(cardPeriod);
			}
		}
		if(createDate!=null) {
			for (CardPeriod cardPeriod : toTimesList) {
				if(cardPeriod.getBigin()!=null&&
						Timing.create.equals(cardPeriod.getBigin())){
					Date date=intToUdate(createDate,
							cardPeriod.getQuantum(), cardPeriod.getQuantumUnit());
					if(Timing.activate.equals(cardPeriod.getEnd())
							&&(td.getEndActivateDate()==null||date.before(td.getEndActivateDate()))) {
							td.setEndActivateDate(date);
					}
				
					if(Timing.bing.equals(cardPeriod.getEnd())
							&&(td.getEndBingDate()==null||date.before(td.getEndBingDate()))) {
							td.setEndBingDate(date);;
					}
					
					if(Timing.cancel.equals(cardPeriod.getEnd())
						&&(td.getEndUseDate()==null||date.before(td.getEndUseDate()))) {
							td.setEndUseDate(date);;
					}
					//toTimesList.remove(cardPeriod);
				}
			}
		}
		
		if(activateDate!=null) {
			for (CardPeriod cardPeriod : toTimesList) {
				if(cardPeriod.getBigin()!=null&&
						Timing.activate.equals(cardPeriod.getBigin())){
					Date date=intToUdate(activateDate,
							cardPeriod.getQuantum(), cardPeriod.getQuantumUnit());
				
					if(Timing.bing.equals(cardPeriod.getEnd())
							&&(td.getEndBingDate()==null||date.before(td.getEndBingDate()))) {
							td.setEndBingDate(date);;
					}
					
					if(Timing.cancel.equals(cardPeriod.getEnd())
						&&(td.getEndUseDate()==null||date.before(td.getEndUseDate()))) {
							td.setEndUseDate(date);;
					}
					//toTimesList.remove(cardPeriod);
				}
			}
		}
		
		if(bingDate!=null) {
			for (CardPeriod cardPeriod : toTimesList) {
				if(cardPeriod.getBigin()!=null&&
						Timing.bing.equals(cardPeriod.getBigin())){
					Date date=intToUdate(bingDate,
							cardPeriod.getQuantum(), cardPeriod.getQuantumUnit());
					
					if(Timing.cancel.equals(cardPeriod.getEnd())
						&&(td.getEndUseDate()==null||date.before(td.getEndUseDate()))) {
							td.setEndUseDate(date);;
					}
					//toTimesList.remove(cardPeriod);
				}
			}
		}
		return td;
		
	}
}
