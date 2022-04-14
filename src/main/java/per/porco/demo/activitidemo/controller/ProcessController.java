package per.porco.demo.activitidemo.controller;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import per.porco.demo.activitidemo.dto.ProcessInstanceDto;
import per.porco.demo.activitidemo.dto.TaskDto;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Porco
 * @Date 2022/4/7
 * @Description
 */
@RestController
@RequestMapping("/processes")
public class ProcessController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @PostMapping
    public void startProcessInstance(@RequestParam("processName") String processName, @RequestParam("user") String user) {
        Map<String, Object> map = new HashMap<>();
        map.put("kesu", StringUtils.isBlank(user) ? "system" : user);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processName, map);
        System.out.println(instance.getId());
        System.out.println(instance.getProcessDefinitionId());
    }

    @GetMapping
    public List<ProcessInstanceDto> findByProcessName(@RequestParam("processName") String processName) {

        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(processName).list();
        return list.stream().map(e -> {
            ProcessInstanceDto dto = new ProcessInstanceDto();
            dto.setId(e.getId());
            dto.setName(e.getName());
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/tasks")
    public List<TaskDto> findTasksByUser(@RequestParam("userId") String userId) {
        List<Task> tasks = taskService.createTaskQuery().list();
        List<TaskDto> dtos = tasks.stream().map(t -> {
            TaskDto dto = new TaskDto();
            dto.setId(t.getId());
            dto.setName(t.getName());
            return dto;
        }).collect(Collectors.toList());
        return dtos;
    }

    @PutMapping("/tasks/n2/{taskId}")
    @Transactional(rollbackFor = Exception.class)
    public void audit(@PathVariable String taskId, @RequestParam("userId") String userId) {
        taskService.claim(taskId, userId);
        Map<String, Object> vars = new HashMap<>();
        vars.put("kesu", userId);
        vars.put("is_transfer", false);
        taskService.complete(taskId, vars);
    }

    @PutMapping("/tasks/n2/{taskId}/2")
    @Transactional(rollbackFor = Exception.class)
    public void audit2(@PathVariable String taskId, @RequestParam("userId") String userId) {
        taskService.claim(taskId, userId);
        Map<String, Object> vars = new HashMap<>();
        vars.put("is_transfer", true);
        vars.put("transfer_to", "porco");
        taskService.complete(taskId, vars);
    }

    @PutMapping("/tasks/n4/{taskId}")
    @Transactional(rollbackFor = Exception.class)
    public void auditN4(@PathVariable String taskId, @RequestParam("userId") String userId) {
        taskService.claim(taskId, userId);
        Map<String, Object> vars = new HashMap<>();
        vars.put("n2_kesu", "manager");
        taskService.complete(taskId, vars);
    }

    @PutMapping("/tasks/n5/{taskId}/1")
    @Transactional(rollbackFor = Exception.class)
    public void auditN51(@PathVariable String taskId, @RequestParam("userId") String userId, @RequestParam("accept") Boolean accept) {
        taskService.claim(taskId, userId);
        Map<String, Object> vars = new HashMap<>();
        vars.put("is_accept", accept);
        taskService.complete(taskId, vars);
    }

}
