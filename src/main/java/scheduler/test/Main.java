package scheduler.test;

import scheduler.base.SchedulerTask;
import scheduler.model.QuartzJobModel;

public class Main {
    public static void main(String[] args) {
        test1();
        test2();
    }

    static void test1() {
        QuartzJobModel quartzJobModel = new QuartzJobModel();
        quartzJobModel.setJobClassName("scheduler.test.Test");
        quartzJobModel.setDescription("我的测试数据");
        quartzJobModel.setCronExpression("0/2 * * * * ?");//2秒
        quartzJobModel.setJobGroup("test-group");
        quartzJobModel.setJobName("Test");
        new SchedulerTask().schedulerJob(quartzJobModel);
    }

    static void test2() {
        QuartzJobModel quartzJobModel = new QuartzJobModel();
        quartzJobModel.setJobClassName("scheduler.test.Test1");
        quartzJobModel.setDescription("我的测试数据1");
        quartzJobModel.setCronExpression("0/2 * * * * ?");//2秒
        quartzJobModel.setJobGroup("test-group1");
        quartzJobModel.setJobName("Test1");
        new SchedulerTask().schedulerJob(quartzJobModel);
    }
}
