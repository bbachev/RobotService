package robotService.core;

import robotService.common.ExceptionMessages;
import robotService.core.interfaces.Controller;
import robotService.models.garages.GarageImpl;
import robotService.models.garages.interfaces.Garage;
import robotService.models.procedures.Charge;
import robotService.models.procedures.Repair;
import robotService.models.procedures.Work;
import robotService.models.procedures.interfaces.Procedure;
import robotService.models.robots.Cleaner;
import robotService.models.robots.Housekeeper;
import robotService.models.robots.interfaces.Robot;

public class ControllerImpl implements Controller {
    private Garage garage;
    private Procedure charge;
    private Procedure repair;
    private Procedure work;

    public ControllerImpl() {
         garage = new GarageImpl();
         charge = new Charge();
         repair = new Repair();
         work = new Work();


    }

    @Override
    public String manufacture(String robotType, String name, int energy, int happiness, int procedureTime) {

      switch (robotType){
          case "Cleaner":
           this.garage.manufacture(new Cleaner(name,energy,happiness,procedureTime));
              break;

          case "Housekeeper":
              this.garage.manufacture(new Housekeeper(name,energy,happiness,procedureTime));
              break;
          default:
              throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_ROBOT_TYPE,robotType));
      }


        return String.format("Robot %s registered successfully",name);
    }



    private void isRobotExists(String name) {
       if (!this.garage.getRobots().containsKey(name)){
           throw new IllegalArgumentException(String.format(ExceptionMessages.NON_EXISTING_ROBOT,name));
       }


    }

    @Override
    public String repair(String robotName, int procedureTime) {
        isRobotExists(robotName);
      this.repair.doService(this.garage.getRobots().get(robotName), procedureTime);


        return String.format("%s had repair procedure",robotName);
    }

    @Override
    public String work(String robotName, int procedureTime) {
        isRobotExists(robotName);
        this.work.doService(this.garage.getRobots().get(robotName),procedureTime);
        return String.format("%s was working for %d hours",robotName,procedureTime);
    }

    @Override
    public String charge(String robotName, int procedureTime) {
        isRobotExists(robotName);
        this.charge.doService(this.garage.getRobots().get(robotName),procedureTime);
        return String.format("%s had charge procedure",robotName);
    }

    @Override
    public String sell(String robotName, String ownerName) {
        isRobotExists(robotName);
        this.garage.getRobots().remove(this.garage.getRobots().get(robotName));

        return String.format("%s bought %s robot",ownerName,robotName) ;
    }

    @Override
    public String history(String procedureType) {
        switch (procedureType){
            case "Charge":
                return this.charge.history();

            case "Repair":
               return this.repair.history();

            case "Work":
               return this.work.history();

        }
        return null;

    }
}
