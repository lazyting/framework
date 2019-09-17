package scheduler.base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import scheduler.model.QuartzJobModel;

import java.util.Date;

public class SchedulerTask {
    private static SchedulerTask schedulerTask;

//    private SchedulerTask() {
//    }
//
//    public static synchronized SchedulerTask getInstance() {
//        if (schedulerTask == null) {
//            schedulerTask = new SchedulerTask();
//        }
//        return schedulerTask;
//    }


    public void schedulerJob(QuartzJobModel quartzJobModel) {
        try {
            Class taskClazz = Class.forName(quartzJobModel.getJobClassName());

            //1.创建Scheduler的工厂
            SchedulerFactory sf = new StdSchedulerFactory();
            //2.从工厂中获取调度器实例
            org.quartz.Scheduler scheduler = sf.getScheduler();

            //3.创建JobDetail
            JobDetail jb = JobBuilder.newJob(taskClazz) // Show 为一个job,是要执行的一个任务。
                    .withDescription(quartzJobModel.getDescription()) //job的描述
                    .withIdentity(quartzJobModel.getJobName(), quartzJobModel.getJobGroup()) //job 的name和group
                    .build();

            //任务运行的时间，SimpleSchedle类型触发器有效
            long time = System.currentTimeMillis() + 3 * 1000L; // 3秒后启动任务
            Date statTime = new Date(time);

            //4.创建Trigger
            //使用SimpleScheduleBuilder或者CronScheduleBuilder
            Trigger t = TriggerBuilder.newTrigger()
                    .withDescription(quartzJobModel.getDescription())
                    .withIdentity(quartzJobModel.getJobName(), quartzJobModel.getJobGroup())
//                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(2)) // 每两秒执行一次
                    .startAt(statTime)  //默认当前时间启动 ,也可以写为：.startNow();
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJobModel.getCronExpression())) //两秒执行一次
                    .build();
            //5.注册任务和定时器
            scheduler.scheduleJob(jb, t);

            //6.启动 调度器
            scheduler.start();
        } catch (Exception e) {
        }
    }
}
