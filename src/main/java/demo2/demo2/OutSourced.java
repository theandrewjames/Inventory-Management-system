package demo2.demo2;

public class OutSourced extends Part{
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return gets companyName
     */
    public String getCompanyName() {return companyName;};

    /**
     *
     * @param name sets companyName
     */
    public void setCompanyName(String name){this.companyName = companyName;};
}
