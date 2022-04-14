package per.porco.demo.activitidemo.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Porco
 * @Date 2022/4/7
 * @Description
 */
@RestController
@RequestMapping("/deploy")
public class DeploymentController {

    @Resource
    private ProcessEngine processEngine;

    @PostMapping
    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("work_order.bpmn20.xml")
                .addClasspathResource("work_order.bpmn20.png")
                .name("客诉工单流程")
                .deploy();
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getKey());
    }
}
