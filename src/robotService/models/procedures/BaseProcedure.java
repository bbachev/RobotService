package robotService.models.procedures;

import robotService.common.ExceptionMessages;
import robotService.models.procedures.interfaces.Procedure;
import robotService.models.robots.interfaces.Robot;

import java.util.ArrayList;
import java.util.Collection;

public abstract class  BaseProcedure implements Procedure {
    protected Collection<Robot> robots;

    protected BaseProcedure() {
        this.robots = new ArrayList<>();
    }


    @Override
    public String history() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        String separator = System.lineSeparator();
        sb.append(separator);
        for (Robot robot : robots) {
            sb.append(robot.toString()).append(separator);
        }



        return sb.toString().trim();
    }

    @Override
    public  void doService(Robot robot, int procedureTime){
        if (robot.getProcedureTime() < procedureTime){
            throw new IllegalArgumentException (ExceptionMessages.INSUFFICIENT_PROCEDURE_TIME);
        }
        robot.setProcedureTime(robot.getProcedureTime() - procedureTime);
        this.robots.add(robot);

    }
}
