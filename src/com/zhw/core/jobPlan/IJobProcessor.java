package com.zhw.core.jobPlan;

import java.util.Properties;


public interface IJobProcessor {

  /**
   * 
   * @功能描述: 从数据库读取配置参数解析后调用的处理主方法
   * @输入参数: @param prop 如：taskId, fileName.从Properties可以取出JobPlan调度起来生成的TaskStatus对象
   *          null表示该TaskStatus不存在，您可以自己创建对象
   * @返回描述: @return Properties  如：RETURN_CODE, RETURN_MESSAGE
   * @异常类型:@throws Exception  运行关键异常必须直接抛出
   */
  Properties process(Properties prop) throws Exception ;
}
