package com.ntsphere.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTime implements Comparable<DateTime>
{
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//  Static methods
	public static final int January = 1;
	public static final int February = 2;
	public static final int March = 3;
	public static final int April = 4;
	public static final int May = 5;
	public static final int June = 6;
	public static final int July = 7;
	public static final int August = 8;
	public static final int September = 9;
	public static final int October = 10;
	public static final int November = 11;
	public static final int December = 12;
	
	public static final int Sunday = Calendar.SUNDAY;	
	public static final int Monday = Calendar.MONDAY;	
	public static final int Tuesday = Calendar.TUESDAY;	
	public static final int Wednesday = Calendar.WEDNESDAY;	
	public static final int Thursday = Calendar.THURSDAY;	
	public static final int Friday = Calendar.FRIDAY;	
	public static final int Saturday = Calendar.SATURDAY;
	
	
	
	
	
	public static DateTime now()
	{
		return new DateTime(Calendar.getInstance());
	}
	
	
	public static DateTime parse(String dateTime, String format) throws Exception
	{
		DateTime dt = new DateTime();
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		dt.getCalendar().setTime(fmt.parse(dateTime));
		
		return dt;
	}
	
	
	public static DateTime parse(String dateTime, String format, Locale locale) throws Exception
	{
		DateTime dt = new DateTime();
		SimpleDateFormat fmt = new SimpleDateFormat(format, locale);
		dt.getCalendar().setTime(fmt.parse(dateTime));
		
		return dt;
	}
	
	
	public static int compare(DateTime left, DateTime right)
	{
		return left.getCalendar().compareTo(right.getCalendar());
	}
	
	
	public static long diffByYear(DateTime left, DateTime right)
	{
		return left.getCalendar().get(Calendar.YEAR) - right.getCalendar().get(Calendar.YEAR);
	}
	
	
	public static long diffByMonth(DateTime left, DateTime right)
	{
		return (left.getCalendar().get(Calendar.YEAR) * 12 + left.getCalendar().get(Calendar.MONTH))
				- (right.getCalendar().get(Calendar.YEAR) * 12 + right.getCalendar().get(Calendar.MONTH));
	}
	
	
	public static long diffByDay(DateTime left, DateTime right)
	{
		long diff = left.getCalendar().getTimeInMillis() - right.getCalendar().getTimeInMillis();
		return diff / (24 * 60 * 60 * 1000);
	}
	
	
	public static long diffByHour(DateTime left, DateTime right)
	{
		long diff = left.getCalendar().getTimeInMillis() - right.getCalendar().getTimeInMillis();
		return diff / (60 * 60 * 1000);
	}
	
	
	public static long diffByMinute(DateTime left, DateTime right)
	{
		long diff = left.getCalendar().getTimeInMillis() - right.getCalendar().getTimeInMillis();
		return diff / (60 * 1000);
	}
	
	
	public static long diffBySecond(DateTime left, DateTime right)
	{
		long diff = left.getCalendar().getTimeInMillis() - right.getCalendar().getTimeInMillis();
		return diff / 1000;
	}
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//  Member methods
	private Calendar calendar;
	
	
	
	
	
	public DateTime()
	{
		this.calendar = Calendar.getInstance();
	}
	
	
	public DateTime(long date)
	{
		this.calendar = Calendar.getInstance();
		this.calendar.setTimeInMillis(date);
		setDate(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1, this.calendar.get(Calendar.DAY_OF_MONTH));
		setTime(this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE), this.calendar.get(Calendar.SECOND), this.calendar.get(Calendar.MILLISECOND));
	}
	
	
	public DateTime(Date date)
	{
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(date);
		setDate(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1, this.calendar.get(Calendar.DAY_OF_MONTH));
		setTime(this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE), this.calendar.get(Calendar.SECOND), this.calendar.get(Calendar.MILLISECOND));
	}
	
	
	public DateTime(DateTime date)
	{
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(date.calendar.getTime());
		setDate(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1, this.calendar.get(Calendar.DAY_OF_MONTH));
		setTime(this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE), this.calendar.get(Calendar.SECOND), this.calendar.get(Calendar.MILLISECOND));
	}
	
	
	public DateTime(Calendar calendar)
	{
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(calendar.getTime());
		setDate(this.calendar.get(Calendar.YEAR), this.calendar.get(Calendar.MONTH) + 1, this.calendar.get(Calendar.DAY_OF_MONTH));
		setTime(this.calendar.get(Calendar.HOUR_OF_DAY), this.calendar.get(Calendar.MINUTE), this.calendar.get(Calendar.SECOND), this.calendar.get(Calendar.MILLISECOND));
	}
	
	
	public DateTime(int year, int month, int day)
	{
		this.calendar = Calendar.getInstance();
		setDate(year, month, day);
		setTime(0, 0, 0, 0);
	}
	
	
	public DateTime(int hour, int minute, int second, int millisecond)
	{
		this.calendar = Calendar.getInstance();
		setDate(0, 0, 0);
		setTime(hour, minute, second, millisecond);
	}
	
	
	public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond)
	{
		this.calendar = Calendar.getInstance();
		setDate(year, month, day);
		setTime(hour, minute, second, millisecond);
	}
	
	
	public String toString()
	{
		return this.calendar.toString();
	}
	
	
	public String toString(String format)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(this.calendar.getTime());
	}
	
	
	public String toString(String format, Locale locale)
	{
		SimpleDateFormat fmt = new SimpleDateFormat(format, locale);
		return fmt.format(this.calendar.getTime());
	}
	
	
	public Calendar getCalendar()	{	return this.calendar;	}
	public int getYear()			{	return this.calendar.get(Calendar.YEAR);			}
	public int getMonth()			{	return this.calendar.get(Calendar.MONTH) + 1;		}
	public int getDay()				{	return this.calendar.get(Calendar.DAY_OF_MONTH);	}
	public int getHour()			{	return this.calendar.get(Calendar.HOUR_OF_DAY);		}
	public int getMinute()			{	return this.calendar.get(Calendar.MINUTE);			}
	public int getSecond()			{	return this.calendar.get(Calendar.SECOND);			}
	public int getMillisecond()		{	return this.calendar.get(Calendar.MILLISECOND);		}
	public int getDayOfWeek()		{	return this.calendar.get(Calendar.DAY_OF_WEEK);		}
	
	
	public DateTime setYear(int value)		{	this.calendar.set(Calendar.YEAR, value);			return this;	}
	public DateTime setMonth(int value)		{	this.calendar.set(Calendar.MONTH, value - 1);		return this;	}
	public DateTime setDay(int value)			{	this.calendar.set(Calendar.DAY_OF_MONTH, value);	return this;	}
	public DateTime setHour(int value)		{	this.calendar.set(Calendar.HOUR_OF_DAY, value);		return this;	}
	public DateTime setMinute(int value)		{	this.calendar.set(Calendar.MINUTE, value);			return this;	}
	public DateTime setSecond(int value)		{	this.calendar.set(Calendar.SECOND, value);			return this;	}
	public DateTime setMillisecond(int value)	{	this.calendar.set(Calendar.MILLISECOND, value);		return this;	}
	
	
	public DateTime addYear(int value)		{	this.calendar.add(Calendar.YEAR, value);		return this;	}
	public DateTime addMonth(int value)		{	this.calendar.add(Calendar.MONTH, value);	return this;	}
	public DateTime addDay(int value)		{	this.calendar.add(Calendar.DATE, value);		return this;	}
	public DateTime addHour(int value)		{	this.calendar.add(Calendar.HOUR_OF_DAY, value);		return this;	}
	public DateTime addMinute(int value)		{	this.calendar.add(Calendar.MINUTE, value);	return this;	}
	public DateTime addSecond(int value)		{	this.calendar.add(Calendar.SECOND, value);	return this;	}
	
	
	
	public DateTime setDate(int year, int month, int day)
	{
		setYear(year);
		setMonth(month);
		setDay(day);
		return this;
	}
	
	
	public DateTime setTime(int hour, int minute, int second, int millisecond)
	{
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		setMillisecond(millisecond);
		return this;
	}
	
	
	public int compareTo(DateTime target)		{ 	return compare(this, target);				}
	public long diffByYear(DateTime target)	{	return diffByYear(this, target);		}
	public long diffByMonth(DateTime target)	{	return diffByMonth(this, target);		}
	public long diffByDay(DateTime target)	{	return diffByDay(this, target);			}
	public long diffByHour(DateTime target)	{	return diffByHour(this, target);		}
	public long diffByMinute(DateTime target)	{	return diffByMinute(this, target);		}
	public long diffBySecond(DateTime target)	{	return diffBySecond(this, target);		}
}
