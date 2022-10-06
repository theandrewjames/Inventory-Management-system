package demo2.demo2;

public class InHouse extends Part{
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @return machineID
     */
    public int getMachineId() {return machineId;};

    /**
     *
     * @param machineId sets machine ID
     */
    public void setMachineId(int machineId){this.machineId = machineId;};
}
