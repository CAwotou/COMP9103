package Assignment1;
public class MyDate {
    public int weekday;
    public int hour;
    public int minute;

    public MyDate(int weekday, int hour, int minute) {
        this.weekday = weekday;
        this.hour = hour;
        this.minute = minute;
    }

    public static int timeDifference(MyDate small,MyDate big){
        int i=0;
        for(i=0;;i++){
            if(small.generateNewDate(small,i).isEqualTo(big)){
                return i;
            }
        }
    }

    public boolean isEqualTo(MyDate other){
        return this.weekday==other.weekday&&
                this.hour==other.hour&&
                this.minute==other.minute;
    }

    public boolean isLessThan(MyDate other) {
        return this.weekday < other.weekday ||
                (this.weekday == other.weekday && this.hour < other.hour) ||
                (this.weekday == other.weekday && this.hour == other.hour && this.minute < other.minute);
    }

    public boolean isGreaterThan(MyDate other) {
        return this.weekday > other.weekday ||
                (this.weekday == other.weekday && this.hour > other.hour) ||
                (this.weekday == other.weekday && this.hour == other.hour && this.minute > other.minute);
    }

    //add minutes and generate a new date
    public MyDate generateNewDate(MyDate departureDate, int duration) {
        if(duration==-60){
            int newMinute = departureDate.minute;
            int newHour = departureDate.hour-1;
            int newWeekday = departureDate.weekday;
            while (newMinute >= 60) {
                newMinute = newMinute - 60;
                newHour++;
            }
            while (newHour >= 24) {
                newHour = newHour - 24;
                newWeekday++;
            }
            while (newWeekday > 7) {
                newWeekday=newWeekday-7;
            }
            while (newHour<0){
                newHour=newHour+24;
                newWeekday=newWeekday-1;
            }
            while (newWeekday<0){
                newWeekday=newWeekday+7;
            }
            MyDate newDate = new MyDate(newWeekday, newHour, newMinute);
            return newDate;
        }
        int newMinute = departureDate.minute + duration;
        int newHour = departureDate.hour;
        int newWeekday = departureDate.weekday;
        while (newMinute >= 60) {
            newMinute = newMinute - 60;
            newHour++;
        }
        while (newHour >= 24) {
            newHour = newHour - 24;
            newWeekday++;
        }
        while (newWeekday > 7) {
            newWeekday=newWeekday-7;
        }
        MyDate newDate = new MyDate(newWeekday, newHour, newMinute);
        return newDate;
    }

    @Override
    public String toString() {
        String weekdayInString=weekday+"";
        String hourInString=hour+"";
        String minuteInString=minute+"";
        if(weekday==0){
            weekdayInString="Sun";
        }
        if(weekday==1){
            weekdayInString="Mon";
        }
        if(weekday==2){
            weekdayInString="Tue";
        }
        if(weekday==3){
            weekdayInString="Wed";
        }
        if(weekday==4){
            weekdayInString="Thu";
        }
        if(weekday==5){
            weekdayInString="Fri";
        }
        if(weekday==6){
            weekdayInString="Sat";
        }
        if(weekday==7){
            weekdayInString="Sun";
        }
        if(weekday==8){
            weekdayInString="Mon";
        }
        if(hour<10){
            hourInString="0"+hour;
        }
        if(minute<10){
            minuteInString="0"+minute;
        }
        return weekdayInString+" "+hourInString+":"+minuteInString;
    }

    public String toString1() {
        String weekdayInString=weekday+"";
        String hourInString=hour+"";
        String minuteInString=minute+"";
        if(weekday==0){
            weekdayInString="Sunday";
        }
        if(weekday==1){
            weekdayInString="Monday";
        }
        if(weekday==2){
            weekdayInString="Tuesday";
        }
        if(weekday==3){
            weekdayInString="Wednesday";
        }
        if(weekday==4){
            weekdayInString="Thursday";
        }
        if(weekday==5){
            weekdayInString="Friday";
        }
        if(weekday==6){
            weekdayInString="Saturday";
        }
        if(weekday==7){
            weekdayInString="Sunday";
        }
        if(weekday==8){
            weekdayInString="Monday";
        }
        if(hour<10){
            hourInString="0"+hour;
        }
        if(minute<10){
            minuteInString="0"+minute;
        }
        return weekdayInString+" "+hourInString+":"+minuteInString;
    }
}
