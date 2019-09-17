package scheduler.model;

import java.io.Serializable;
import java.util.Date;

public class QuartzJobModel implements Serializable {

    private static final long serialVersionUID = -4544664150921048568L;

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务分组
     */
    private String jobGroup;

    /**
     * 执行类
     */
    private String jobClassName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 任务状态
     */
    private String triggerState;

    /**
     * 修改之前的任务名称
     */
    private String oldJobName;

    /**
     * 修改之前的任务分组
     */
    private String oldJobGroup;

    /**
     * 描述
     */
    private String description;

    private Date createtime;

    private String creatorcode;

    private Date updatetime;

    private String updatercode;

    private Integer recStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getOldJobName() {
        return oldJobName;
    }

    public void setOldJobName(String oldJobName) {
        this.oldJobName = oldJobName;
    }

    public String getOldJobGroup() {
        return oldJobGroup;
    }

    public void setOldJobGroup(String oldJobGroup) {
        this.oldJobGroup = oldJobGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreatorcode() {
        return creatorcode;
    }

    public void setCreatorcode(String creatorcode) {
        this.creatorcode = creatorcode;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdatercode() {
        return updatercode;
    }

    public void setUpdatercode(String updatercode) {
        this.updatercode = updatercode;
    }

    public Integer getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(Integer recStatus) {
        this.recStatus = recStatus;
    }

    @Override
    public String toString() {
        return "QuartzJobModel{" +
                "id=" + id +
                ", jobName=" + jobName +
                ", jobGroup=" + jobGroup +
                ", jobClassName=" + jobClassName +
                ", cronExpression=" + cronExpression +
                ", triggerState=" + triggerState +
                ", oldJobName=" + oldJobName +
                ", oldJobGroup=" + oldJobGroup +
                ", description=" + description +
                ", createtime=" + createtime +
                ", creatorcode=" + creatorcode +
                ", updatetime=" + updatetime +
                ", updatercode=" + updatercode +
                ", recStatus=" + recStatus +
                "}";
    }
}
