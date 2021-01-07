// package model;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.time.LocalTime;
// import java.util.ArrayList;
// import java.util.Calendar;
// import java.util.List;

// import org.junit.jupiter.api.Test;

// public class OpeningHoursTest {
//     OpeningHours op;
    
//     @Test
//     public void addOpeningHoursTest(){
//         op=new OpeningHours(null,null,null,null,null,null,null);
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(8).withMinute(0));
//         list.add(LocalTime.now().withHour(9).withMinute(0));
//         op.setFriday(list);
//         assertEquals(list, op.friday);
//     }

//     @Test
//     public void cantAddOpeningTimeWithoutClosingTime(){
//         op=new OpeningHours(null,null,null,null,null,null,null);
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(8).withMinute(0));
//         op.setFriday(list);
//         assertEquals("[]",op.friday.toString());
//     }

//     @Test
//     public void isOpen(){
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()-1).withMinute(0));
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()+1).withMinute(0));
//         op=new OpeningHours(list,list,list,list,list,list,list);
//         assertTrue(op.isOpen());
//     }

//     @Test
//     public void isNotOpen(){
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()+1).withMinute(0));
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()-1).withMinute(0));
//         op=new OpeningHours(list,list,list,list,list,list,list);
//         assertTrue(!op.isOpen());
//     }

//     @Test
//     public void isNotOpenAnymore(){
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()-2).withMinute(0));
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()-1).withMinute(0));
//         op=new OpeningHours(list,list,list,list,list,list,list);
//         assertTrue(!op.isOpen());
//     }

//     @Test
//     public void isNotOpenYet(){
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()+1).withMinute(0));
//         list.add(LocalTime.now().withHour(LocalTime.now().getHour()+2).withMinute(0));
//         op=new OpeningHours(list,list,list,list,list,list,list);
//         assertTrue(!op.isOpen());
//     }

//     @Test
//     public void openThisDay(){
//         op=new OpeningHours(null,null,null,null,null,null,null);
//         List<LocalTime> list = new ArrayList<>();
//         list.add(LocalTime.now().withHour(0).withMinute(0));
//         list.add(LocalTime.now().withHour(23).withMinute(59));
//         op.setFriday(list);
//         Calendar c=Calendar.getInstance();
//         if(c.get(Calendar.DAY_OF_WEEK)==6)
//             assertTrue(op.isOpen());
//         else
//             assertTrue(!op.isOpen());
//     }
// }
