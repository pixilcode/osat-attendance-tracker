package model;

import java.util.GregorianCalendar;

public class Date implements Comparable<Date> {
	
	private final int year;
	private final int month;
	private final int day;
	
	public Date() {
		
		// Month is 0-based in Gregorian Calendar
		GregorianCalendar today = new GregorianCalendar();
		year = today.get(GregorianCalendar.YEAR);
		month = today.get(GregorianCalendar.MONTH) - 1;
		day = today.get(GregorianCalendar.DAY_OF_MONTH);
		
	}
	
	public Date(GregorianCalendar date) {
		year = date.get(GregorianCalendar.YEAR);
		month = date.get(GregorianCalendar.MONTH);
		day = date.get(GregorianCalendar.DAY_OF_MONTH);
	}
	
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int year() {
		return year;
	}
	
	public int month() {
		return month;
	}
	
	public int day() {
		return day;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Date))
			return false;
		Date other = (Date) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	public GregorianCalendar toGregorianCalendar() {
		return new GregorianCalendar(year, month, day);
	}
	
	@Override
	public String toString() {
		return month + "/" + day + "/" + year;
	}

	public int compareTo(Date date) {
		
		if(year != date.year)
			return (year > date.year) ? 1 : -1;
		else if(month != date.month)
			return (month > date.month) ? 1 : -1;
		else if(day != date.day)
			return (day > date.day) ? 1 : -1;
		else
			return 0;
		
	}
	
}
