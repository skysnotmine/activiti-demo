package per.porco.demo.activitidemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Porco
 * @Date 2022/4/7
 * @Description
 */
@Data
public class ProcessInstanceDto implements Serializable {

    private static final long serialVersionUID = 512304581081772623L;

    private String id;

    private String name;
}
